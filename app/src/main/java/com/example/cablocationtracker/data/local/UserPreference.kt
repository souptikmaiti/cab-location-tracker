package com.example.cablocationtracker.data.local

import android.content.Context
import com.example.cablocationtracker.util.PrefUtil

class UserPreference {
    companion object{
        val USER_NAME = "USER_NAME"
        val PASSWORD = "PASSWORD"
        val IS_LOGGED_IN = "IS_LOGGED_IN"
        val IS_SUPER_USER = "IS_SUPER_USER"
    }

    fun setUserName(context: Context, userName: String) = PrefUtil.setString(context, USER_NAME, userName)

    fun getUserName(context: Context): String? = PrefUtil.getString(context, USER_NAME, "")

    fun setPassword(context: Context, password: String) = PrefUtil.setString(context, PASSWORD, password)

    fun getPassword(context: Context): String? = PrefUtil.getString(context, PASSWORD, "")

    fun setIsLoggedIn(context: Context, isLoggedIn: Boolean) = PrefUtil.setBoolean(context, IS_LOGGED_IN, isLoggedIn)

    fun getIsLoggedIn(context: Context): Boolean = PrefUtil.getBoolean(context, IS_LOGGED_IN)

    fun setIsSuperUser(context: Context, isSuperUser: Boolean) = PrefUtil.setBoolean(context, IS_SUPER_USER, isSuperUser)

    fun getIsSuperUser(context: Context): Boolean = PrefUtil.getBoolean(context, IS_SUPER_USER)

}