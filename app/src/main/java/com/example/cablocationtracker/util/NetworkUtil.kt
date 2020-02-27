package com.example.cablocationtracker.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings

object NetworkUtil {

    fun isNetworkAvailable(appContext: Context): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activteNetworkInfo = connectivityManager.activeNetworkInfo
        return activteNetworkInfo != null && activteNetworkInfo.isConnected
    }

    fun isInFlightMode(appContext: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(appContext.contentResolver, Settings.System.AIRPLANE_MODE_ON, 0) != 0
        } else {
            return Settings.Global.getInt(appContext.contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
        }
    }
}