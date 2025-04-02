package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ActivityClothOnePosition : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloth_one_position)

        val email : String = intent.getStringExtra("emailToClothOne").toString()
        val clothName : String = intent.getStringExtra("clothOneName").toString()
        val clothImageName : String = intent.getStringExtra("clothOneImg").toString()
        val clothDescription : String = intent.getStringExtra("clothOneDescription").toString()
        val clothLink : String = intent.getStringExtra("clothOneLinkShort").toString()
        var figure: String = intent.getStringExtra("clothOneFigure").toString()
        figure = figure.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val clothCategory : String = intent.getStringExtra("clothOneCategory").toString()

        val image : ImageView = findViewById<View>(R.id.imageViewClothOneImg) as ImageView
        image.setImageResource(this.resources.getIdentifier(clothImageName, "drawable", this.packageName))

        val name : TextView = findViewById<View>(R.id.textViewClothOneName) as TextView
        name.text = clothName.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        val brand : TextView = findViewById<View>(R.id.textViewClothOneBrand) as TextView
        brand.text = clothDescription

        val link: TextView = findViewById<View>(R.id.textViewClothOneLink) as TextView
        link.text = "Ссылка на товар $clothCategory: \n$clothLink"

        val toHome: ImageButton = findViewById<View>(R.id.imageButtonHomeIconTwo) as ImageButton
        toHome.setOnClickListener {
            val intent = Intent("android.intent.action.ActivityHome")
            intent.putExtra("email", email);
            startActivity(intent)
        }

        val toCloth: ImageButton = findViewById<View>(R.id.imageButtonClothIconTwo) as ImageButton
        toCloth.setOnClickListener {
            val intent = Intent("android.intent.action.ClothActivity")
            intent.putExtra("emailToCloth", email);
            intent.putExtra("figureWoman", figure)
            startActivity(intent)
        }
    }
}