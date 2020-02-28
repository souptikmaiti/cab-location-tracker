package com.example.cablocationtracker.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.cablocationtracker.CabLocationTrackerApplication
import com.google.gson.GsonBuilder

class UserPreference {
    companion object{
        val PREFERENCE_NAME = "CabLocationTracker"
        val USER_NAME = "USER_NAME"
        val USER_EMAIL = "USER_EMAIL"
        val USER_ID = "USER_ID"
        val IS_LOGGED_IN = "IS_LOGGED_IN"
        val IS_SUPER_USER = "IS_SUPER_USER"
        val MOBILE_NUMBER = "MOBILE_NUMBER"
        val appContext = CabLocationTrackerApplication.appContext
    }

    private fun getPreference(): SharedPreferences =
        appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

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

    fun setUserName(userName: String) =  setString(USER_NAME, userName)

    fun setUserEmail(userEmail: String) =  setString(USER_EMAIL, userEmail)

    fun setUserId(userid: String) =  setString(USER_ID, userid)

    fun setMobileNumber(mobile: String) =  setString(MOBILE_NUMBER, mobile)

    fun setIsLoggedIn(isLoggedIn: Boolean) =  setBoolean(IS_LOGGED_IN, isLoggedIn)

    fun setIsSuperUser(isSuperUser: Boolean) =  setBoolean(IS_SUPER_USER, isSuperUser)


    fun getUserName(): String? =  getString(USER_NAME, "")

    fun getUserEmail(): String? =  getString(USER_EMAIL, "")

    fun getUserId(): String? =  getString(USER_ID, "")

    fun getMobileNumber(): String? =  getString(MOBILE_NUMBER, "")

    fun getIsLoggedIn(): Boolean =  getBoolean(IS_LOGGED_IN)

    fun getIsSuperUser(): Boolean =  getBoolean(IS_SUPER_USER)

}