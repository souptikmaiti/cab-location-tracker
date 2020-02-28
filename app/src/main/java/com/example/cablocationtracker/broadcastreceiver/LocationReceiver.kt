package com.example.cablocationtracker.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.widget.Toast
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.repository.Repository
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LocationReceiver : BroadcastReceiver() {
    companion object{
        val ACTION_LOCATION_UPDATE = "com.example.cablocationtracker.UPDATE_LOCATION"
        val repo = Repository.instance
        var waitFlag = false
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
            updateLocationInFirebase(latitude!!, longitude!!)
            Toast.makeText(context, "latitude=$latitude ,longitude=$longitude",Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLocationInFirebase(latitude: Double, longitude: Double) {
        val locInfo: SmallLocation = SmallLocation(latitude, longitude, System.currentTimeMillis())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if(!waitFlag){
                    waitFlag = true
                    repo.updateLocation(locInfo)
                    waitFlag = false
                }

            }catch (e: Exception){

            }
        }

    }
}
