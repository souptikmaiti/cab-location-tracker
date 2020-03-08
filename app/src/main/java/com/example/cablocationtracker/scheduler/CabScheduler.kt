package com.example.cablocationtracker.scheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.repository.Repository
import com.example.cablocationtracker.util.Toaster
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CabScheduler: JobService() {
    companion object{
        val repo = Repository.instance
        val TAG = CabScheduler::class.java.simpleName
    }
    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job Stopped")
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "onStartJob")
        val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.lastLocation.addOnSuccessListener {
            val smallLocation = SmallLocation(it.latitude, it.longitude, System.currentTimeMillis())
            Toaster.showShort(applicationContext, smallLocation.latitude.toString() + ", " + smallLocation.latitude.toString())
            CoroutineScope(Dispatchers.IO).launch {
                repo.updateLocation(smallLocation)
                jobFinished(params, false)
            }
        }
        return true
    }
}