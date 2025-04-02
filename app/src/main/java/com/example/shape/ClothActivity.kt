package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class ClothActivity : AppCompatActivity() {
    private val sizeArray : Int = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth)

        val email : String = intent.getStringExtra("emailToCloth").toString()
        var figure: String = intent.getStringExtra("figureWoman").toString()
        figure = figure.replaceFirstChar { it.lowercase(Locale.ROOT) }
        val gender = "женщина"

        val threadHelperId = ThreadHelper(-2)
        val threadHelperCloth = ThreadHelper(ArrayList<Cloth>())
        val db = ClothDBHelper()
        val figureId = db.findTypeFigure(figure, threadHelperId)
        val clothes : ArrayList<Cloth> = db.findClothes(figureId, gender, threadHelperCloth)


        val clothTexts = addTextView(ArrayList(), sizeArray)
        changeTextView(clothTexts, sizeArray, clothes)

        val clothBut = addImageButton(ArrayList(), sizeArray)
        changeImageButton(clothBut, sizeArray, clothes)


        val toHome: ImageButton = findViewById<View>(R.id.imageButtonHomeIcon) as ImageButton
        toHome.setOnClickListener {
            val intent = Intent("android.intent.action.ActivityHome")
            intent.putExtra("email", email);
            startActivity(intent)
        }

        clothBut[0].setOnClickListener {
            clickImageButton(email, figure, clothes[0])
        }
        clothBut[1].setOnClickListener {
            clickImageButton(email, figure, clothes[1])
        }
        clothBut[2].setOnClickListener {
            clickImageButton(email, figure, clothes[2])
        }
        clothBut[3].setOnClickListener {
            clickImageButton(email, figure, clothes[3])
        }
        clothBut[4].setOnClickListener {
            clickImageButton(email, figure, clothes[4])
        }
        clothBut[5].setOnClickListener {
            clickImageButton(email, figure, clothes[5])
        }
        clothBut[6].setOnClickListener {
            clickImageButton(email, figure, clothes[6])
        }
        clothBut[7].setOnClickListener {
            clickImageButton(email, figure, clothes[7])
        }
        clothBut[8].setOnClickListener {
            clickImageButton(email, figure, clothes[8])
        }
        clothBut[9].setOnClickListener {
            clickImageButton(email, figure, clothes[9])
        }
        clothBut[10].setOnClickListener {
            clickImageButton(email, figure, clothes[10])
        }
        clothBut[11].setOnClickListener {
            clickImageButton(email, figure, clothes[11])
        }
        clothBut[12].setOnClickListener {
            clickImageButton(email, figure, clothes[12])
        }
        clothBut[13].setOnClickListener {
            clickImageButton(email, figure, clothes[13])
        }
        clothBut[14].setOnClickListener {
            clickImageButton(email, figure, clothes[14])
        }
        clothBut[15].setOnClickListener {
            clickImageButton(email, figure, clothes[15])
        }
        clothBut[16].setOnClickListener {
            clickImageButton(email, figure, clothes[16])
        }
        clothBut[17].setOnClickListener {
            clickImageButton(email, figure, clothes[17])
        }
        clothBut[18].setOnClickListener {
            clickImageButton(email, figure, clothes[18])
        }
        clothBut[19].setOnClickListener {
            clickImageButton(email, figure, clothes[19])
        }
    }

    private fun addTextView(clothTexts: ArrayList<TextView>, size: Int): ArrayList<TextView> {
        var i = 1
        while (i < size + 1){
            clothTexts.add(findViewById<View>(this.resources.getIdentifier("textCloth$i", "id", this.packageName)) as TextView)
            i++
        }
        return clothTexts
    }

    private fun changeTextView(clothTexts: ArrayList<TextView>, size: Int, clothes : ArrayList<Cloth>){
        var i = 0
        while (i < size){
            clothTexts[i].text = clothes[i].category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            i++
        }
    }

    private fun addImageButton(clothBut: ArrayList<ImageButton>, size: Int): ArrayList<ImageButton> {
        var i = 1
        while (i < size + 1){
            clothBut.add(findViewById<View>(this.resources.getIdentifier("butCloth$i", "id", this.packageName)) as ImageButton)
            i++
        }
        return clothBut
    }

    private fun changeImageButton(clothBut: ArrayList<ImageButton>, size: Int, clothes : ArrayList<Cloth>){
        var i = 0
        while (i < size){
            clothBut[i].setImageResource(this.resources.getIdentifier(clothes[i].imageName, "drawable", this.packageName))
            i++
        }
    }

    private fun clickImageButton(email: String, figure: String, clothes: Cloth){
        val intent = Intent("android.intent.action.ActivityClothOnePosition")
        intent.putExtra("emailToClothOne", email)
        intent.putExtra("clothOneName", clothes.clothesName)
        intent.putExtra("clothOneImg", clothes.imageName)
        intent.putExtra("clothOneDescription", clothes.description)
        intent.putExtra("clothOneFigure", figure)
        intent.putExtra("clothOneCategory", clothes.category)
        intent.putExtra("clothOneLinkShort", clothes.linkShort)
        startActivity(intent)
    }
}