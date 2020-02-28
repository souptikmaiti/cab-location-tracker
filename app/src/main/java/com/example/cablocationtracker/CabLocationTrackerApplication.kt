package com.example.cablocationtracker

import android.app.Application
import android.content.Context

class CabLocationTrackerApplication: Application() {

    companion object{
        @JvmStatic
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}