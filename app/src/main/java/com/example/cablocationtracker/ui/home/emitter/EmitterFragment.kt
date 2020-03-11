package com.example.cablocationtracker.ui.home.emitter


import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.cablocationtracker.R
import com.example.cablocationtracker.foregroundservice.LocationForegroundService
import com.example.cablocationtracker.scheduler.CabScheduler
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
        val JOB_ID = 56
        val REFRESH_INTERVAL: Long = 15*60*1000
    }
    lateinit var jobScheduler: JobScheduler
    lateinit var jobInfo: JobInfo
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

        tv_welcome.text = "Welcome " + homeActivity.currentUser?.userName
        btn_start.setOnClickListener {
            //startService()
            scheduleJob()
        }

        btn_stop.setOnClickListener {
            //stopService()
            cancelJob()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if(ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //startService()
                scheduleJob()
            }else{
                requestPermissions(permissionlist,REQUEST_PERMISSION)
            }
            return
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //startService()
                scheduleJob()
            }else{
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION)
            }
            return
        }
    }

    private fun scheduleJob(){
        val mComponentName = ComponentName(context!!, CabScheduler::class.java)


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            jobInfo = JobInfo.Builder(JOB_ID, mComponentName)
                //.setPeriodic(15*60*1000)
                .setPersisted(true)
                .setMinimumLatency(REFRESH_INTERVAL)
                .build()
        }else{
            jobInfo = JobInfo.Builder(JOB_ID, mComponentName)
                .setPeriodic(REFRESH_INTERVAL)
                .setPersisted(true)
                //.setMinimumLatency(15*60*1000)
                .build()
        }
        val resultCode: Int = jobScheduler.schedule(jobInfo)

        if (resultCode == JobScheduler.RESULT_SUCCESS) Log.d(TAG, "Job Scheduled")
        else Log.d(TAG, "Job not Scheduled")
    }

    private fun cancelJob(){
        jobScheduler?.cancel(JOB_ID)
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
                        //startService()
                        scheduleJob()
                    }else{
                        Toaster.showShort(context!!, "Permission denied")
                    }
                    return
                }
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                        //startService()
                        scheduleJob()
                    }else{
                        Toaster.showShort(context!!, "Permission denied")
                    }
                    return
                }
            }
        }
    }

}
