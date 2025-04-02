package com.example.shape

import android.content.res.AssetManager
import android.graphics.Bitmap
import com.example.shape.Keys.DIM_BATCH_SIZE
import com.example.shape.Keys.DIM_IMG_SIZE_X
import com.example.shape.Keys.DIM_IMG_SIZE_Y
import com.example.shape.Keys.DIM_PIXEL_SIZE
import com.example.shape.Keys.INPUT_SIZEw
import com.example.shape.Keys.INPUT_SIZEh
import com.example.shape.Keys.LABEL_PATH
import com.example.shape.Keys.MAX_RESULTS
import com.example.shape.Keys.MODEL_PATH
import org.tensorflow.lite.Interpreter
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList
import io.reactivex.Single

class ImageClassifier constructor(private val assetManager: AssetManager) {

    private var interpreter: Interpreter? = null
    private var labelProb: Array<FloatArray>
    private val labels = Vector<String>()
    private val intValues by lazy { IntArray(INPUT_SIZEw * INPUT_SIZEh) }
    private var imgData: ByteBuffer

    init {
        try {
            val br = BufferedReader(InputStreamReader(assetManager.open(LABEL_PATH)))
            while (true) {
                val line = br.readLine() ?: break
                labels.add(line)
            }
            br.close()
        } catch (e: IOException) {
            throw RuntimeException("Problem reading label file!", e)
        }
        labelProb = Array(1) { FloatArray(labels.size) }
        imgData =
            ByteBuffer.allocateDirect(4 * DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE)
        imgData.order(ByteOrder.nativeOrder())
        try {
            interpreter = Interpreter(loadModelFile(assetManager, MODEL_PATH))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        imgData.rewind()
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until DIM_IMG_SIZE_X) {
            for (j in 0 until DIM_IMG_SIZE_Y) {
                val value = intValues[pixel++]
                imgData.putFloat(((value shr 16 and 0xFF) - 128) / 128.0f)
                imgData.putFloat(((value shr 8 and 0xFF) - 128) / 128.0f)
                imgData.putFloat(((value and 0xFF) - 128) / 128.0f)
            }
        }
    }

    private fun loadModelFile(assets: AssetManager, modelFilename: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelFilename)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun recognizeImage(bitmap: Bitmap): Single<List<Result>> {
        return Single.just(bitmap).flatMap {
            convertBitmapToByteBuffer(it)
            interpreter!!.run(imgData, labelProb)
            val pq = PriorityQueue<Result>(3,
                Comparator<Result> { lhs, rhs ->
                    // Intentionally reversed to put high confidence at the head of the queue.
                    (rhs.confidence!!).compareTo(lhs.confidence!!)
                })
            for (i in labels.indices) {
                pq.add(
                    Result(
                        "" + i,
                        if (labels.size > i) labels[i] else "unknown",
                        labelProb[0][i],
                        null
                    )
                )
            }
            val recognitions = ArrayList<Result>()
            val recognitionsSize = pq.size.coerceAtMost(MAX_RESULTS)
            for (i in 0 until recognitionsSize) pq.poll()?.let { it1 -> recognitions.add(it1) }
            return@flatMap Single.just(recognitions)
        }
    }

    fun close() {
        interpreter?.close()
    }
}