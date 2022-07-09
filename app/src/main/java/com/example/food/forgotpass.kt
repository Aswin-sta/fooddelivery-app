package com.example.food

import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast


class forgotpass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpass)

        var goback = findViewById<TextView>(R.id.goback)
        goback.setOnClickListener {
            startActivity(Intent(this, login::class.java))
            finish()
        }
    }

    fun submitfun(view: android.view.View) {
        var email = findViewById<EditText>(R.id.email).text.toString().trim()
        if (email.isNotEmpty()) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Reset e-mail sent succesfully", Toast.LENGTH_LONG)
                            .show()
                        startActivity(Intent(this, login::class.java))
                        finish()
                    }
                }
        } else {
            Toast.makeText(this, "Enter Email address", Toast.LENGTH_LONG).show()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, login::class.java))
        finish()
    }
}