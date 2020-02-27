package com.example.cablocationtracker.ui.home.emitter


import android.Manifest
import android.app.job.JobScheduler
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.cablocationtracker.R
import com.example.cablocationtracker.foregroundservice.LocationForegroundService
import com.example.cablocationtracker.ui.home.HomeActivity
import com.example.cablocationtracker.util.Toaster
import kotlinx.android.synthetic.main.fragment_emitter.*

class EmitterFragment : Fragment() {

    companion object{
        @JvmStatic
        fun newInstance(): EmitterFragment{
            return EmitterFragment()
        }
        const val REQUEST_PERMISSION = 111
        val TAG = EmitterFragment::class.java.simpleName
    }
    lateinit var jobScheduler: JobScheduler
    lateinit var homeActivity: HomeActivity
    val permissionlist = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emitter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity = activity as HomeActivity
        jobScheduler = homeActivity!!.getSystemService(AppCompatActivity.JOB_SCHEDULER_SERVICE) as JobScheduler

        btn_start.setOnClickListener {
            startService()
        }

        btn_stop.setOnClickListener {
            stopService()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                startService()
            }else{
                requestPermissions(permissionlist,REQUEST_PERMISSION)
            }
            return
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                startService()
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
            }
            return
        }
    }

    private fun startService() {
        val serviceIntent = Intent(homeActivity, LocationForegroundService::class.java)
        serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android")
        ContextCompat.startForegroundService(homeActivity, serviceIntent)
    }

    private fun stopService() {
        val serviceIntent = Intent(homeActivity, LocationForegroundService::class.java)
        homeActivity.stopService(serviceIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_PERMISSION -> {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                        startService()
                    }else{
                        Toaster.showShort(context!!, "Permission denied")
                    }
                    return
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        startService()
                    }else{
                        Toaster.showShort(context!!, "Permission denied")
                    }
                    return
                }
            }
        }
    }

}
