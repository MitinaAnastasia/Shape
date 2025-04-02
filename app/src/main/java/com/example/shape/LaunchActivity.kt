package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        val toRegister : Button = findViewById<View>(R.id.buttonRegister) as Button
        toRegister.setOnClickListener{
            val intent = Intent("android.intent.action.ActivityRegister")
            startActivity(intent)
        }

        val toLogin : Button = findViewById<View>(R.id.buttonLogin) as Button
        toLogin.setOnClickListener{
            val intent = Intent("android.intent.action.ActivityLogin")
            startActivity(intent)
        }
    }
}