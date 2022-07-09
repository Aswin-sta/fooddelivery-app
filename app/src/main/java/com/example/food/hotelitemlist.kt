package com.example.food

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.adapter.adapter
import com.example.food.adapter.adapterhorizontal
import com.example.food.data.hoteldata
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class hotelitemlist : AppCompatActivity(),CellClickListener {

    val db=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hotelitemlist)


        //intent extract
        var id1=intent.getStringExtra("id_value").toString()
        var collect=intent.getStringExtra("collection_value")

        //
        db.collection("hotels").document("$id1").collection("foodlist")
            .get()
            .addOnSuccessListener {
                var data2=ArrayList<data1>()
                var recycler3:RecyclerView=findViewById(R.id.recyclerView3)
                recycler3.layoutManager=  LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                for(document in it)
                {
                       var hotel:TextView=findViewById(R.id.hotelnameinlistview)
                       hotel.setText("${id1}")
                       var nonveg=document.data?.get("Vegnon").toString()
                       var price=document.data?.get("Price").toString()
                    recycler3.adapter = adapter1(this@hotelitemlist, data2, this, "${id1}")
                    Log.d("data","data id:${document.data}")
                    var img=document.data?.get("image").toString()
                    data2.add(data1(img, "${document.id}", "$price", "$nonveg"))
                    Log.d("tag","documetszzz:${document.id}")
                }
            }





    }
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,menu::class.java))
        finish()
    }
    override fun onCellClickListener(id:String,collection:String) {
        val intent= Intent(this@hotelitemlist,FoodView::class.java)
        intent.putExtra("id_value",id)
        intent.putExtra("collection_value",collection)
        startActivity(intent)
        finish()
    }

    override fun onCellClickListener1(id:String,collection:String) {
        TODO("Not yet implemented")
    }
}
