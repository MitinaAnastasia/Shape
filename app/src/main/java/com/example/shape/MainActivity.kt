package com.example.shape

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shape.Keys.INPUT_SIZEh
import com.example.shape.Keys.INPUT_SIZEw
import io.reactivex.rxkotlin.subscribeBy
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {
    private val CHOOSE_IMAGE = 1001
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier
    var image: ImageView? = null
    val Pick_image = 1
    var txt: TextView? = null
    var mainTitle: String = ""
    var ansTitle: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        classifier = ImageClassifier(assets)

        val PickImage: Button = findViewById<View>(R.id.button) as Button
        PickImage.setOnClickListener {
            choosePicture()
        }

        val username : String = intent.getStringExtra("username").toString()
        val emailM : String = intent.getStringExtra("emailToMain").toString()

        val toHome : Button = findViewById<View>(R.id.buttonNext) as Button
        toHome.setOnClickListener{
            when (mainTitle) {
                "hourglass" -> ansTitle += "песочные часы"
                "apple" -> ansTitle += "яблоко"
                "rectangle" -> ansTitle += "прямоугольник"
            }
            val db = UserDBHelper()
            db.findAndUpdateTypeFigure(username, ansTitle)
            val intent = Intent("android.intent.action.ActivityHome")
            intent.putExtra("email", emailM)
            intent.putExtra("typeFig", ansTitle)
            startActivity(intent)
        }
    }


    private fun choosePicture() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, Pick_image)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Pick_image && resultCode == Activity.RESULT_OK)
            try {
                val stream = data!!.data?.let { contentResolver!!.openInputStream(it) }
                if (::photoImage.isInitialized) photoImage.recycle()
                photoImage = BitmapFactory.decodeStream(stream)
                photoImage = Bitmap.createScaledBitmap(photoImage, INPUT_SIZEw, INPUT_SIZEh, false)
                var imageResult: ImageView = findViewById<View>(R.id.imageResult) as ImageView
                imageResult.setImageBitmap(photoImage)
                var txtResult: TextView = findViewById<View>(R.id.txtResult) as TextView;

                classifier.recognizeImage(photoImage).subscribeBy(
                    onSuccess = {
                        txtResult.text = it.toString()
                        if(it.get(0).confidence != null && it.get(0).title != null && it.get(1).confidence != null && it.get(1).title != null && it.get(2).confidence != null && it.get(2).title != null) {
                            mainTitle = findFigureType(it.get(0).confidence!!, it.get(0).title!!, it.get(1).confidence!!, it.get(1).title!!, it.get(2).confidence!!, it.get(2).title!!)
                        }
                    }
                )
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
    }

    fun findFigureType(confidiens1: Float, title1: String, confidiens2: Float, title2: String, confidiens3: Float, title3: String): String {
        val firstStep: Float = Math.max(confidiens1, confidiens2)
        val secondStep: Float = Math.max(firstStep, confidiens3)
        if(confidiens1.compareTo(secondStep) == 0) {
            return title1
        }
        if(confidiens2.compareTo(secondStep) == 0){
            return title2
        }
        if(confidiens3.compareTo(secondStep) == 0){
            return title3
        }
        return ""
    }

    /*
        private fun choosePicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
                    val stream = data!!.data?.let { contentResolver!!.openInputStream(it) }
                    if (::photoImage.isInitialized) photoImage.recycle()
                    photoImage = BitmapFactory.decodeStream(stream)
                    photoImage = Bitmap.createScaledBitmap(photoImage, INPUT_SIZE, INPUT_SIZE, false)
                    imageResult?.setImageBitmap(photoImage)
                    classifier.recognizeImage(photoImage).subscribeBy(
                        onSuccess = {
                            txtResult?.text = it.toString()
                        }
                    )
        }
    }
    */*/

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}
