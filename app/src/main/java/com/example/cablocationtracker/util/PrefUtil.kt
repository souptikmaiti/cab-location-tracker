package com.example.cablocationtracker.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object PrefUtil {
    private val PREFERENCE_NAME = "CabLocationTracker"

    private fun getPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getBoolean(context: Context, key: String): Boolean {
        return getPreference(context).getBoolean(key, false)
    }

    fun setBoolean(context: Context, key: String, value: Boolean) {
        getPreference(context).edit().putBoolean(key, value).apply()
    }

    fun getInt(context: Context, key: String, defaultValue: Int): Int {
        return getPreference(context).getInt(key, defaultValue)
    }

    fun setInt(context: Context, key: String, value: Int) {
        getPreference(context).edit().putInt(key, value).apply()
    }

    fun getFloat(context: Context, key: String, defaultValue: Float): Float {
        return getPreference(context).getFloat(key, defaultValue)
    }

    fun setFloat(context: Context, key: String, value: Float) {
        getPreference(context).edit().putFloat(key, value).apply()
    }

    fun getString(context: Context, key: String, defaultValue: String?): String? {
        return getPreference(context).getString(key, defaultValue)
    }

    fun setString(context: Context, key: String, value: String?) {
        getPreference(context).edit().putString(key, value).apply()
    }

    fun<T> getObject(context: Context, key: String, defaultValue: T?, className: Class<T>): T?{
        // will return null if no value for the given key found
        var string: String? = getString(context, key, null) ?: return defaultValue

        //else convert the string to object and return
        return GsonBuilder()
            .create()
            .fromJson(string, className)
    }

    fun<T> setObject(context: Context, key: String, value: T?){
        var string: String? = null
        if(value != null){
            //convert the object to string and then store in sharedpref
            string = GsonBuilder()
                .create()
                .toJson(string)
        }
        setString(context, key, string)
    }
}