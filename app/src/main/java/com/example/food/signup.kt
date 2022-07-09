package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class signup : AppCompatActivity() {

    val auth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signup : Button =findViewById(R.id.signup)
        signup.setOnClickListener {
            register()
        }

        val signin : TextView =findViewById(R.id.signin)
        signin.setOnClickListener {
            startActivity(Intent(this,login::class.java))
            finish()
        }

    }

    private fun register() {
        val email1 = findViewById<EditText>(R.id.email)
        val name1 = findViewById<EditText>(R.id.fullname)
        val pass1 = findViewById<EditText>(R.id.password1)
        val cpass1 = findViewById<EditText>(R.id.password)

        var email = email1.text.toString()
        var name = name1.text.toString()
        var password = pass1.text.toString()
        var cpass = cpass1.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email address", Toast.LENGTH_LONG).show()
        }

        if (name.isEmpty()) {
            Toast.makeText(this, "Name field cannot be Empty ", Toast.LENGTH_LONG).show()
        }

        else if (password.isEmpty()) {
            Toast.makeText(this, "Enter your password ", Toast.LENGTH_LONG).show()
        }
        if (password.length <6) {
            Toast.makeText(this, "password length should be greater than 6 ", Toast.LENGTH_LONG).show()
        }
        else if (cpass.isEmpty()) {
            Toast.makeText(this, "please confirm your password ", Toast.LENGTH_LONG).show()
        }

        if(password==cpass)
        {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        val user = auth.currentUser
                        Log.d("user1", "createUserWithEmail:success ,userid:${user?.uid}")
                        startActivity(Intent(this,menu::class.java))
                        val id=user?.uid.toString()
                        usernameStore(name,email,id)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        // updateUI(null)
                    }
                }
        }
        else
        {
            Toast.makeText(this, "passwords don't match", Toast.LENGTH_LONG).show()
            startActivity(Intent(this,signup::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,menu::class.java))
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, login::class.java))
        finish()
    }
    fun usernameStore(name:String,email:String,uid:String) {
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "email" to "$email",
            "name" to "$name"
        )

        db.collection("users").document(uid).set(user)
            .addOnSuccessListener {
                Toast.makeText(
                    this, "success", Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this, "failed", Toast.LENGTH_SHORT
                ).show()
            }

    }


}


