
package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class login : AppCompatActivity() {
   companion object
   {
       const val RC_SIGN_IN=34
   }

    private lateinit var googleSignInClient :GoogleSignInClient
    val auth=FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var signInbtn :Button=findViewById(R.id.signIn)
        signInbtn.setOnClickListener {

           var  email=findViewById<EditText>(R.id.email).text.toString()
            var  password=findViewById<EditText>(R.id.password).text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success")
                            val user = auth.currentUser
                            startActivity(Intent(this, menu::class.java))
                            finish()
                            //updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Toast.makeText(
                                baseContext, "Invalid credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                            // updateUI(null)

                        }
                    }
            }
            else
            {
                Toast.makeText(this,"please enter credentials",Toast.LENGTH_SHORT).show()
            }
        }







        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id_auth))
            .requestEmail()
            .build()

       googleSignInClient = GoogleSignIn.getClient(this, gso)

  val signupbutton :TextView=findViewById(R.id.signupbtn)
        signupbutton.setOnClickListener {
            startActivity(Intent(this,signup::class.java))
            finish()
        }

    }



    fun gsignin(view: android.view.View) {

        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
        //Toast.makeText(this,"button adichu",Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("Error90", "Google sign in failed", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser!=null)
        {
            startActivity(Intent(this,menu::class.java))
            finish()
        }


    }


    private fun firebaseAuthWithGoogle(idToken: String) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    startActivity(Intent(this,menu::class.java))
                    Log.d("user1","FirebaseAuthwithgoogle:${user?.displayName}")

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)

                }
            }




    }

    fun forgot(view: android.view.View) {
        startActivity(Intent(this,forgotpass::class.java))
        finish()
    }


}