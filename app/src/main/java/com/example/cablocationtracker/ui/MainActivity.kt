package com.example.cablocationtracker.ui

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cablocationtracker.R
import com.example.cablocationtracker.broadcastreceiver.LocationReceiver
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    companion object{
        val REQUEST_FINE_LOCATION = 111
        val REQUEST_BACKGROUND_LOCATION = 222
    }

    var locationRequest: LocationRequest? = null
    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    if(checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        updateLocation()
                    }else{
                        requestPermissions(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                            REQUEST_BACKGROUND_LOCATION
                        )
                    }
                }else{
                    updateLocation()
                }
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_FINE_LOCATION
                )
            }
        }
    }

    private fun updateLocation() {
        buildLocationRequest()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient?.requestLocationUpdates(locationRequest, getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent? {
        val intent: Intent = Intent(this, LocationReceiver::class.java)
        intent.action = LocationReceiver.ACTION_LOCATION_UPDATE
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 5000
        locationRequest?.fastestInterval = 3000
        //locationRequest?.smallestDisplacement = 10f
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_FINE_LOCATION -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateLocation()
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_BACKGROUND_LOCATION ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    updateLocation()
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
