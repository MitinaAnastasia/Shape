package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val email : String = intent.getStringExtra("email").toString()
        val user = User("", "", "", "", -1)
        val db = UserDBHelper()
        val userProf : User = db.selectUserForProfile(email, user)
        val threadHelper = ThreadHelper("")
        val figureName : String = db.selectTypeFig(userProf.figureTypeUserId, threadHelper)
        val figureNameReg : String = intent.getStringExtra("typeFig").toString()

        val profileText: TextView = findViewById<View>(R.id.nameProfileText) as TextView;
        profileText.text = userProf.username
        val emailText: TextView = findViewById<View>(R.id.data1ProfileText) as TextView;
        emailText.text = "Email: " + email
        val genderText: TextView = findViewById<View>(R.id.data2ProfileText) as TextView;
        genderText.text = "Пол: " + userProf.genderName
        val figureText: TextView = findViewById<View>(R.id.data3ProfileText) as TextView;
        figureText.text = "Тип фигуры: " + figureName
        if(figureName == "без типа"){
            figureText.text = "Тип фигуры: " + figureNameReg
            if(figureText.text == "Тип фигуры: null"){
                val intent = Intent("android.intent.action.MainActivity")
                intent.putExtra("emailToMain", email)
                intent.putExtra("username", userProf.username)
                startActivity(intent)
            }
        }
        if(userProf.genderName == "Женщина") {
            val toCloth: ImageButton = findViewById<View>(R.id.imageButtonClothIcon) as ImageButton
            toCloth.setOnClickListener {
                val intent = Intent("android.intent.action.ClothActivity")
                intent.putExtra("emailToCloth", email);
                intent.putExtra("figureWoman", figureText.text.toString().removePrefix("Тип фигуры: "))
                startActivity(intent)
            }
        }
        if(userProf.genderName == "Мужчина") {
            val toManCloth: ImageButton = findViewById<View>(R.id.imageButtonClothIcon) as ImageButton
            toManCloth.setOnClickListener {
                val intent = Intent("android.intent.action.ActivityClothMan")
                intent.putExtra("emailToManCloth", email);
                intent.putExtra("figureMan", figureText.text.toString().removePrefix("Тип фигуры: "))
                startActivity(intent)
            }
        }

        val toLogin : Button = findViewById<View>(R.id.buttonExitProfile) as Button
        toLogin.setOnClickListener{
            val intent = Intent("android.intent.action.ActivityLogin")
            startActivity(intent)
        }
    }
}