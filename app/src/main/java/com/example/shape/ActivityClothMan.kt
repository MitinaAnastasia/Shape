package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import java.util.*

class ActivityClothMan : AppCompatActivity() {
    private val sizeArray : Int = 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth_man)

        val email : String = intent.getStringExtra("emailToManCloth").toString()
        var figure: String = intent.getStringExtra("figureMan").toString()
        figure = figure.replaceFirstChar { it.lowercase(Locale.ROOT) }
        if(figure == "песочные часы"){
            figure = "прямоугольник"
        }
        val gender = "мужчина"

        val threadHelperId = ThreadHelper(-2)
        val threadHelperCloth = ThreadHelper(ArrayList<Cloth>())
        val db = ClothDBHelper()
        val figureId = db.findTypeFigure(figure, threadHelperId)
        val clothes : ArrayList<Cloth> = db.findClothes(figureId, gender, threadHelperCloth)


        val clothTexts = addTextView(ArrayList(), sizeArray)
        changeTextView(clothTexts, sizeArray, clothes)

        val clothBut = addImageButton(ArrayList(), sizeArray)
        changeImageButton(clothBut, sizeArray, clothes)


        val toHome: ImageButton = findViewById<View>(R.id.imageButtonHomeIconMan) as ImageButton
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
    }

    private fun addTextView(clothTexts: ArrayList<TextView>, size: Int): ArrayList<TextView> {
        var i = 1
        while (i < size + 1){
            clothTexts.add(findViewById<View>(this.resources.getIdentifier("textClothMan$i", "id", this.packageName)) as TextView)
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
            clothBut.add(findViewById<View>(this.resources.getIdentifier("butClothMan$i", "id", this.packageName)) as ImageButton)
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
        val intent = Intent("android.intent.action.ActivityClothManOnePosition")
        intent.putExtra("emailToClothOneMan", email)
        intent.putExtra("clothOneNameMan", clothes.clothesName)
        intent.putExtra("clothOneImgMan", clothes.imageName)
        intent.putExtra("clothOneDescriptionMan", clothes.description)
        intent.putExtra("clothOneFigureMan", figure)
        intent.putExtra("clothOneCategoryMan", clothes.category)
        intent.putExtra("clothOneLinkShortMan", clothes.linkShort)
        startActivity(intent)
    }
}