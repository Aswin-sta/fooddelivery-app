package com.example.food


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class smenu : AppCompatActivity()
{
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smenu)

        auth = FirebaseAuth.getInstance()
        var db = FirebaseFirestore.getInstance()
        var user = auth.currentUser
        var id = user?.uid.toString()
        var nameinAbout: TextView = findViewById(R.id.user)
            db.collection("users").document("$id")
            .get().addOnSuccessListener{document->
                if(document!=null)
                {   var userdetail=document.data?.get("name").toString()
                    nameinAbout.setText(userdetail)
                }
               else
                {
                    Log.d("tag","usenameerror")
                }
        }


    }

    fun logout(view: android.view.View) {
        auth.signOut()
        startActivity(Intent(this,login::class.java))
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,menu::class.java))
        finish()
    }

}


