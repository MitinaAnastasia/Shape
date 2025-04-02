package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var email: EditText = findViewById(R.id.emailLogEditText)
        var password: EditText = findViewById(R.id.passwordLogEditText)

        val toNextPage : Button = findViewById<View>(R.id.buttonNextPageAfterLogin) as Button
        toNextPage.setOnClickListener{
            val threadHelper = ThreadHelper(-2)
            val db = UserDBHelper()
            val element: Int = db.findUser(email.text.toString(), password.text.toString(), threadHelper)
            if(element <= 0){
                Toast.makeText(this, "Такого пользователя не найдено, проверьте введенные данные", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent("android.intent.action.ActivityHome")
                intent.putExtra("email", email.text.toString());
                startActivity(intent)
            }
        }

        val toRegPage : Button = findViewById<View>(R.id.buttonBackToReg) as Button
        toRegPage.setOnClickListener{
            val intent = Intent("android.intent.action.ActivityRegister")
            startActivity(intent)
        }
    }
}