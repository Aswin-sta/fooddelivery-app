package com.example.food

import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*

class payment : AppCompatActivity() {
    var addr:String="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        val money=intent.getStringExtra("amount").toString()
        Log.d("tag","money:$money")
        val pay:Button=findViewById(R.id.payButton)
        pay.setText("pay :${money}")



    }

    fun pay(view: View) {
        val view=View.inflate(this@payment,R.layout.confirmdialog,null)
        val builder= AlertDialog.Builder(this@payment)
        builder.setView(view)
        var dialog=builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@payment
                ,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                menu.REQUEST_PERMISSION_REQUEST_CODE
            )
        }
        else
        {
            var locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            //now getting address from latitude and longitude

            val geocoder = Geocoder(this@payment, Locale.getDefault())
            var addresses: List<Address>

            LocationServices.getFusedLocationProviderClient(this@payment)
                .requestLocationUpdates(locationRequest, object : LocationCallback() {
                    override  fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@payment)
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
                            var location:TextView=findViewById(R.id.location)
                            location.setText("${address.toString() }")
                            var z=address.toString()
                            addr.replace("a","${z}")
                        }
                    }
                }, Looper.getMainLooper())
        }
    }

    override fun onStart() {
        super.onStart()
        getCurrentLocation()
    }

    fun change(view: View) {
        var location:TextView=findViewById(R.id.location)
        location.setText(" Take Away")
    }

}