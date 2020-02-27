package com.example.cablocationtracker.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.cablocationtracker.CabLocationTrackerApplication
import com.google.gson.GsonBuilder

object PrefUtil {
    private val PREFERENCE_NAME = "CabLocationTracker"
    var application: Application = CabLocationTrackerApplication()
    private fun getPreference(): SharedPreferences =
        application.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getBoolean(key: String): Boolean {
        return getPreference().getBoolean(key, false)
    }

    fun setBoolean(key: String, value: Boolean) {
        getPreference().edit().putBoolean(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return getPreference().getInt(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        getPreference().edit().putInt(key, value).apply()
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return getPreference().getFloat(key, defaultValue)
    }

    fun setFloat(key: String, value: Float) {
        getPreference().edit().putFloat(key, value).apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return getPreference().getString(key, defaultValue)
    }

    fun setString(key: String, value: String?) {
        getPreference().edit().putString(key, value).apply()
    }

    fun<T> getObject(key: String, defaultValue: T?, className: Class<T>): T?{
        // will return null if no value for the given key found
        var string: String? = getString(key, null) ?: return defaultValue

        //else convert the string to object and return
        return GsonBuilder()
            .create()
            .fromJson(string, className)
    }

    fun<T> setObject(key: String, value: T?){
        var string: String? = null
        if(value != null){
            //convert the object to string and then store in sharedpref
            string = GsonBuilder()
                .create()
                .toJson(string)
        }
        setString(key, string)
    }
}