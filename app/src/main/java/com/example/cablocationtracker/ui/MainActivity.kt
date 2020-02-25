package com.example.cablocationtracker.ui

import android.Manifest
import android.app.job.JobScheduler
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.cablocationtracker.R
import com.example.cablocationtracker.foregroundservice.LocationForegroundService
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        const val REQUEST_PERMISSION = 111
        val TAG = MainActivity::class.java.simpleName
    }
    lateinit var jobScheduler: JobScheduler

    val permissionlist = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        btn_start.setOnClickListener {
            startService()
        }

        btn_stop.setOnClickListener {
            stopService()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                startService()
            }else{
                requestPermissions(permissionlist,REQUEST_PERMISSION)
            }
            return
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                startService()
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
            }
            return
        }
    }

    private fun startService() {
        val serviceIntent = Intent(this, LocationForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(this, LocationForegroundService::class.java)
        stopService(serviceIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_PERMISSION -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                        startService()
                    }else{
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        startService()
                    }else{
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                    return
                }
            }
        }
    }
}
