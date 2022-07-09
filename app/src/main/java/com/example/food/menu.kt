package com.example.food


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food.adapter.adapter
import com.example.food.adapter.adapterhorizontal
import com.example.food.data.data
import com.example.food.data.hoteldata
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import android.os.Looper
import android.widget.ImageButton
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import com.google.firebase.firestore.QuerySnapshot
import java.util.*


class menu : AppCompatActivity(),CellClickListener {

//   private val imagelist= mutableListOf<Int>()
//    private val namelist= mutableListOf<String>()
//    private val imagelist1= mutableListOf<Int>()
//    private val namelist1= mutableListOf<String>()
    companion object {
        const val REQUEST_PERMISSION_REQUEST_CODE = 2000
    }
    val db=FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()
        var user = auth.currentUser

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //
        var cart: ImageButton =findViewById(R.id.cart)
        cart.setOnClickListener{
            val intent=Intent(this@menu, com.example.food.cart::class.java)
            startActivity(intent)
            finish()
        }
        var home:ImageButton=findViewById(R.id.home)
        home.setOnClickListener{
            val intent=Intent(this@menu, com.example.food.menu::class.java)
            startActivity(intent)
            finish()
        }




        db.collection("hotels")
            .get()
            .addOnSuccessListener {
                val data1 = ArrayList<hoteldata>()
                val recyclerview = findViewById<RecyclerView>(R.id.recycler)
                recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

                for(document in it)
                {       val hotellocation=document.data?.get("location").toString()
                    val price42=document.data?.get("pricefor2").toString()
                    val rating=document.data?.get("rating").toString()
                    var img=document.data?.get("image").toString()
                    data1.add(hoteldata("${document.id}",img,"$hotellocation","$price42","$rating"))
                    recyclerview.adapter = adapter(this@menu,data1,this,"hotels")
                    Log.d("tag","documets:${document.id}")
                }
            }



        val recyclerview2 = findViewById<RecyclerView>(R.id.horizontalrecycler)
        recyclerview2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        // ArrayList of class ItemsViewModel
        db.collection("curratedCollections")
            .get()
            .addOnSuccessListener {
                val data = ArrayList<data>()


                // Setting the Adapter with the recyclerview

                for (document in it)
                {
                    var location:String=document.data?.get("Hotelname").toString()
                    var pr:String=document.data?.get("Price").toString()
                    var img=document.data?.get("image").toString()
                    recyclerview2.adapter= adapterhorizontal(this@menu,data,this,"curratedCollections")
                    data.add(data(img, "${document.id}" ,"$location","$pr"))
                }
            }


//on create end
    }





    override fun onStart() {
        super.onStart()

            getCurrentLocation()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this@menu, "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@menu,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_PERMISSION_REQUEST_CODE
            )
        }
        else
        {
            var locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            //now getting address from latitude and longitude

            val geocoder = Geocoder(this@menu, Locale.getDefault())
            var addresses: List<Address>

            LocationServices.getFusedLocationProviderClient(this@menu)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                   override  fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@menu)
                            .removeLocationUpdates(this)
                        if (locationResult !== null && locationResult.locations.size > 0) {
                            var locIndex = locationResult.locations.size - 1

                            var latitude = locationResult.locations.get(locIndex).latitude
                            var longitude = locationResult.locations.get(locIndex).longitude


                            addresses = geocoder.getFromLocation(latitude, longitude, 1)
                            addresses = geocoder.getFromLocation(latitude, longitude, 1)
                            var address: String = addresses[0].getAddressLine(0)
                            var loca = findViewById<TextView>(R.id.loc)
                            var loca1 = findViewById<TextView>(R.id.loc2)
                            Log.d("address","address:$address")
                            loca.text = address.toString().split(",".toRegex())[0]
                            loca1.text = address.toString().split(",".toRegex())[1]
                        }
                    }
                }, Looper.getMainLooper())
        }
    }







    fun sidemenufun(view: android.view.View) {
        startActivity(Intent(this,smenu::class.java))
        finish()

    }
//recycler fun

//    private fun addToList(title:String,image:Int){
//
//        imagelist.add(image)
//        namelist.add(title)
//    }
//
//
//    private fun  postToList() {
//        for (i in 1..20) {
//            addToList("Alfahami$i", R.drawable.testimage)
//        }
//    }

    override fun onCellClickListener(id:String,collection:String) {
       val intent= Intent(this@menu,FoodView::class.java)
        intent.putExtra("id_value",id)
        intent.putExtra("collection_value",collection)
        startActivity(intent)
        finish()
    }
    override fun onCellClickListener1(id: String,collection: String) {
        val intent= Intent(this@menu,hotelitemlist::class.java)
        intent.putExtra("id_value",id)
        intent.putExtra("collection_value",collection)
        startActivity(intent)
        finish()
    }

    fun search(view: View) {
       val intent=Intent(this,search::class.java)
        startActivity(intent)

        Toast.makeText(this,"asdhajdhqw",Toast.LENGTH_SHORT).show()

    }



}