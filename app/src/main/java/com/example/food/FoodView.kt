package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FoodView : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    var price1 = 0
    var Tprice1 = 0
    var typez: String = "a"
    var totalp: String = "a"
    var img1: String = "a"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_view)
        var qty = 1
        var foodname: TextView = findViewById(R.id.foodname)
        var hotelname: TextView = findViewById(R.id.providername)
        val addd: Button = findViewById(R.id.plusbutton)
        val subb: Button = findViewById(R.id.subbutton)
        val no: TextView = findViewById(R.id.qty)
        var pic: ImageView = findViewById(R.id.foodimageinfoodview)
        val Addfood: Button = findViewById(R.id.AddButton)
        //price
        val p: TextView = findViewById(R.id.price)
        val Tp: TextView = findViewById(R.id.totalprice)


        //
        var id1 = intent.getStringExtra("id_value").toString()
        var collect = intent.getStringExtra("collection_value")
        foodname.setText(id1)


        //food retrival
        if (collect.equals("curratedCollections")) {
//            Toast.makeText(this, "user path", Toast.LENGTH_LONG).show()
            db.collection("$collect").document("$id1").get()
                .addOnSuccessListener { document ->
                    var vegnon: String = document.data?.get("Vegnon").toString()
                    typez = typez.replace("a", "$vegnon")
                    var Hotelname: String = document.data?.get("Hotelname").toString()
                    var price = document.data?.get("Price").toString()
                    var img = document.data?.get("image").toString()
                    Log.d("tag", "returns:${price}")
                    p.setText(price)
                    Glide.with(this@FoodView)
                        .load(img).into(pic)
                    hotelname.setText("$Hotelname")

                    price1 = price.toInt()
                    Tprice1 = price.toInt()
                    Tp.text = Tprice1.toString()
                    totalp = totalp.replace("a", "$Tprice1")
                    img1 = img1.replace("a", "$img")
                    addd.setOnClickListener {
                        if (qty < 10) {
                            qty++
                            Tprice1 = Tprice1 + price1
                            Tp.text = Tprice1.toString()
                            no.text = qty.toString()

                        } else {
                            Toast.makeText(this, "Quantity exceeded", Toast.LENGTH_SHORT).show()
                        }
                    }


                    subb.setOnClickListener {
                        if (qty > 1) {
                            Tprice1 = Tprice1 - price1
                            Tp.text = Tprice1.toString()
                            qty--
                            no.text = qty.toString()
                        }
                    }

                }


        } else {
            Toast.makeText(this, "hotel path", Toast.LENGTH_LONG).show()
            Log.d("tag", "returnsybryer:${collect}")
            db.collection("hotels").document("$collect").collection("foodlist").document("$id1")
                .get()
                .addOnSuccessListener { document ->
                    var type = document.data?.get("Vegnon").toString()
                    typez = typez.replace("a", "$type")
                    var price = document.data?.get("Price").toString()
                    Log.d("tag", "returns:${document.id}")
                    p.setText(price)
                    Tp.text = price.toString()
                    var img = document.data?.get("image").toString()
                    Glide.with(this@FoodView)
                        .load(img).into(pic)
                    hotelname.setText("$type")
                    var price1 = price.toInt()
                    var Tprice1 = price.toInt()
                    Tp.text = Tprice1.toString()
                    img1 = img1.replace("a", "$img")
                    totalp = totalp.replace("a", "$Tprice1")
                    addd.setOnClickListener {
                        if (qty < 10) {
                            qty++
                            Tprice1 = Tprice1 + price1
                            Tp.text = Tprice1.toString()
                            no.text = qty.toString()

                        } else {
                            Toast.makeText(this, "Quantity exceeded", Toast.LENGTH_SHORT).show()
                        }
                    }


                    subb.setOnClickListener {
                        if (qty > 1) {
                            Tprice1 = Tprice1 - price1
                            Tp.text = Tprice1.toString()
                            qty--
                            no.text = qty.toString()
                        }
                    }

                }


        }







        Addfood.setOnClickListener {
            var user = auth.currentUser
            var userid = user?.uid
            val intent = Intent(this@FoodView, cart::class.java)
            startActivity(intent)
            finish()
            val cart = hashMapOf(

                "name" to "$id1",
                "price" to "$totalp",
                "quantity" to "$qty",
                "vegnon" to "$typez",
                "image" to "$img1"
            )
            Log.d("tag", "item added cart:$cart")

            db.collection("users").document("$userid").collection("cart").document("$id1").set(cart)
                .addOnSuccessListener {
                    Log.d("tag", "items added to database")
                }
                .addOnFailureListener {
                    Log.d("tag", "items adding failed")
                }
        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, menu::class.java))
        finish()
    }

    fun back(view: View) {
        startActivity(Intent(this, menu::class.java))
        finish()
    }
}