package com.example.shape

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class ActivityRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val spinner: Spinner = findViewById<View>(R.id.genderSpinner) as Spinner

        var genders = arrayOf("Мужчина", "Женщина")
        val adapter = ArrayAdapter(this, R.layout.spinner_style, genders)
        spinner.adapter = adapter

        var username: EditText = findViewById(R.id.nameEditText)
        var email: EditText = findViewById(R.id.emailEditText)
        var password:EditText = findViewById(R.id.passwordEditText)

        val toNextPage : Button = findViewById<View>(R.id.buttonNextRegister) as Button
        toNextPage.setOnClickListener{
            val threadHelper1 = ThreadHelper(-2)
            val threadHelper2 = ThreadHelper(-2)
            val db1 = UserDBHelper()
            if(username.text.isEmpty()){
                Toast.makeText(this, "Проверьте поле Имя пользователя", Toast.LENGTH_LONG).show()
            }
            else if(email.text.isEmpty() || !email.text.contains("@")){
                Toast.makeText(this, "Проверьте поле Электронная почта", Toast.LENGTH_LONG).show()
            }
            else if (password.text.isEmpty() || password.text.length < 10) {
                Toast.makeText(this, "Пароль должен быть больше 9 символов", Toast.LENGTH_LONG).show()
            }
            else if (db1.checkUserByUsername(username.text.toString(), threadHelper1) > 0){
                Toast.makeText(this, "Пользователь с таким именем уже существует, измените Имя Пользователя", Toast.LENGTH_LONG).show()
            }
            else if (db1.checkUserByEmail(email.text.toString(), threadHelper2) > 0){
                Toast.makeText(this, "Пользователь с такой электронной почтой уже существует, измените Электронную почту", Toast.LENGTH_LONG).show()
            }
            else {
                val user = User(username.text.toString(), email.text.toString(), password.text.toString(), spinner.selectedItem.toString(), 1)
                val db = UserDBHelper()
                db.addUser(user)
                val intent = Intent("android.intent.action.MainActivity")
                intent.putExtra("username", username.text.toString())
                intent.putExtra("emailToMain", email.text.toString())
                startActivity(intent)
            }
        }

        val toLogPage : Button = findViewById<View>(R.id.buttonToLog) as Button
        toLogPage.setOnClickListener{
            val intent = Intent("android.intent.action.ActivityLogin")
            startActivity(intent)
        }
    }
}