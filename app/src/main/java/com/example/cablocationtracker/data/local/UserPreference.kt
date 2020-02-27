package com.example.cablocationtracker.data.local

import android.content.Context
import com.example.cablocationtracker.util.PrefUtil

class UserPreference {
    companion object{
        val USER_NAME = "USER_NAME"
        val USER_EMAIL = "USER_EMAIL"
        val USER_ID = "USER_ID"
        val IS_LOGGED_IN = "IS_LOGGED_IN"
        val IS_SUPER_USER = "IS_SUPER_USER"
        val MOBILE_NUMBER = "MOBILE_NUMBER"
    }

    fun setUserName(userName: String) = PrefUtil.setString(USER_NAME, userName)

    fun setUserEmail(userEmail: String) = PrefUtil.setString(USER_EMAIL, userEmail)

    fun setUserId(userid: String) = PrefUtil.setString(USER_ID, userid)

    fun setMobileNumber(mobile: String) = PrefUtil.setString(MOBILE_NUMBER, mobile)

    fun setIsLoggedIn(isLoggedIn: Boolean) = PrefUtil.setBoolean(IS_LOGGED_IN, isLoggedIn)

    fun setIsSuperUser(isSuperUser: Boolean) = PrefUtil.setBoolean(IS_SUPER_USER, isSuperUser)


    fun getUserName(): String? = PrefUtil.getString(USER_NAME, "")

    fun getUserEmail(): String? = PrefUtil.getString(USER_EMAIL, "")

    fun getUserId(): String? = PrefUtil.getString(USER_ID, "")

    fun getMobileNumber(): String? = PrefUtil.getString(MOBILE_NUMBER, "")

    fun getIsLoggedIn(): Boolean = PrefUtil.getBoolean(IS_LOGGED_IN)

    fun getIsSuperUser(): Boolean = PrefUtil.getBoolean(IS_SUPER_USER)

}