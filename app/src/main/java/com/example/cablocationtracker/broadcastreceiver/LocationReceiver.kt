package com.example.cablocationtracker.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import com.google.android.gms.location.LocationResult

class LocationReceiver : BroadcastReceiver() {
    companion object{
        val ACTION_LOCATION_UPDATE = "com.example.cablocationtracker.UPDATE_LOCATION"
    }
    override fun onReceive(context: Context, intent: Intent) {
        var latitude :Double? = null
        var longitude: Double? = null
        if (intent != null){
            val action = intent.action
            if(ACTION_LOCATION_UPDATE.equals(action)){
                val locationResult: LocationResult? = LocationResult.extractResult(intent)
                val location: Location? = locationResult?.lastLocation

                location?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                }

            }
        }

        if (latitude != null && longitude != null){
            Toast.makeText(context, "latitude=$latitude ,longitude=$longitude",Toast.LENGTH_SHORT).show()
        }
    }
}
