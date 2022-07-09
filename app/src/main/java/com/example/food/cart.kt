package com.example.food

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.adapter.adapter
import com.example.food.data.cartdata
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class cart : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth


    var totamount=0
    val data6=ArrayList<cartdata>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var Totalp: TextView =findViewById(R.id.totprice)
        val db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        var user=auth.currentUser
        var id=user?.uid.toString()

        var back=findViewById<ImageButton>(R.id.backzz)
        back.setOnClickListener {
            startActivity(Intent(this,menu::class.java))
            finish()
        }
        fun check() {
            if (data6.isEmpty()) {
                var noitem: TextView = findViewById(R.id.noitemcart)
                noitem.isVisible=true
            }
            else
            {
                var noitem: TextView = findViewById(R.id.noitemcart)
                noitem.isVisible=false
            }
        }

        var proceed: Button =findViewById(R.id.proceedButton)
        proceed.setOnClickListener {

            val intent = Intent(this, payment::class.java)
            intent.putExtra("amount", "$totamount")
            Log.d("tag", "aooount:$totamount")
            startActivity(intent)
            finish()
            addorder()


        }
        var recycler4:RecyclerView=findViewById(R.id.cartrecycler)
        recycler4.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        db.collection("users").document("$id").collection("cart").get()
            .addOnSuccessListener {

                val swipetoDel=object:swipetodel(){
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val position=viewHolder.adapterPosition
                        val delItem:cartdata=data6[position]
                        var itemname:String=delItem.name
                        var p:String=delItem.price
                        var q:String=delItem.quantity
                        data6.removeAt(position)
                        check()
                        db.collection("users").document("$id").collection("cart").document("$itemname")
                            .delete()
                            .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error deleting document", e) }
                        totamount=totamount-(p.toInt()*q.toInt())
                        Totalp.setText(totamount.toString())
                        Log.d("tag","deleted item:$delItem")
                        recycler4.adapter?.notifyItemRemoved(position)
                    }
                }
                val ItemTouchHelper=ItemTouchHelper(swipetoDel)
                ItemTouchHelper.attachToRecyclerView(recycler4)

                for (documents in it)
                {
                    var name=documents.data?.get("name").toString()
                    var price=documents.data?.get("price").toString()
                    var quantity=documents.data?.get("quantity").toString()
                    var vegnon=documents.data?.get("vegnon").toString()
                    var img=documents.data?.get("image").toString()
                    totamount=totamount+(price.toInt()*quantity.toInt())
                    Totalp.setText(totamount.toString())
                    recycler4.adapter=Adaptercart(this,data6)
                    Log.d("tag","cart contents:${documents.data}")
                    data6.add(cartdata("$img","$name","$price","$vegnon","$quantity"))
                    check()
                }
            }
        check()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addorder() {
        val db= FirebaseFirestore.getInstance()
        auth= FirebaseAuth.getInstance()
        var user=auth.currentUser
        var id=user?.uid.toString()
        val formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())

        for (i in 1..data6.size) {
            val cart = hashMapOf(
                "data" to "$data6[i].name",

                )
            Log.d("tag", "item added oder:$cart")

            db.collection("users").document("$id").collection("orders").document("$formatter")
                .set(cart)
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
        startActivity(Intent(this,menu::class.java))
        finish()
    }

}