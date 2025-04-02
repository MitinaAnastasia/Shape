package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ActivityClothManOnePosition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth_man_one_position)

        val email : String = intent.getStringExtra("emailToClothOneMan").toString()
        val clothName : String = intent.getStringExtra("clothOneNameMan").toString()
        val clothImageName : String = intent.getStringExtra("clothOneImgMan").toString()
        val clothDescription : String = intent.getStringExtra("clothOneDescriptionMan").toString()
        val clothLink : String = intent.getStringExtra("clothOneLinkShortMan").toString()
        var figure: String = intent.getStringExtra("clothOneFigureMan").toString()
        figure = figure.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val clothCategory : String = intent.getStringExtra("clothOneCategoryMan").toString()

        val image : ImageView = findViewById<View>(R.id.imageViewClothManOneImg) as ImageView
        image.setImageResource(this.resources.getIdentifier(clothImageName, "drawable", this.packageName))

        val name : TextView = findViewById<View>(R.id.textViewClothManOneName) as TextView
        name.text = clothName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        val brand : TextView = findViewById<View>(R.id.textViewClothManOneBrand) as TextView
        brand.text = clothDescription

        val link: TextView = findViewById<View>(R.id.textViewClothManOneLink) as TextView
        link.text = "Ссылка на товар $clothCategory: \n$clothLink"

        val toHome: ImageButton = findViewById<View>(R.id.imageButtonHomeIconTwoMan) as ImageButton
        toHome.setOnClickListener {
            val intent = Intent("android.intent.action.ActivityHome")
            intent.putExtra("email", email);
            startActivity(intent)
        }

        val toCloth: ImageButton = findViewById<View>(R.id.imageButtonClothIconTwoMan) as ImageButton
        toCloth.setOnClickListener {
            val intent = Intent("android.intent.action.ActivityClothMan")
            intent.putExtra("emailToManCloth", email);
            intent.putExtra("figureMan", figure)
            startActivity(intent)
        }
    }
}