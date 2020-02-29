package com.example.cablocationtracker.repository

import android.content.Context
import com.example.cablocationtracker.data.local.UserPreference
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.data.remote.FirebaseConnect

class Repository private constructor(){

    val firebaseConnect: FirebaseConnect
            get() = FirebaseConnect()

    val userPreference: UserPreference
        get() = UserPreference()


    companion object{
        val instance = Repository()
    }

    fun getCurrentUser() = firebaseConnect.getCurrentUser()

    fun signOut() = firebaseConnect.signOut()

    suspend fun registerUser(email:String, password: String) = firebaseConnect.registerUser(email, password)

    suspend fun loginUser(email:String, password: String) = firebaseConnect.loginUser(email, password)

    suspend fun addUser(user: User) = firebaseConnect.addUser(user)

    suspend fun updateLocation(locationinfo: SmallLocation) = firebaseConnect.updateLocation(locationinfo)

    suspend fun getUserInfo(uid: String) = firebaseConnect.getCurrentUserInfo(uid)

    fun getUserList() = firebaseConnect.getAllUsers()

    fun getLocation(uid: String) = firebaseConnect.getLocation(uid)

    fun saveUserInfoLocally(userInfo: User){
        userPreference.setUserName(userInfo.userName ?: "")
        userPreference.setUserId(userInfo.id ?: "")
        userPreference.setUserEmail(userInfo.email ?: "")
        userPreference.setMobileNumber(userInfo.mobile ?: "")
        userPreference.setMobileNumber(userInfo.mobile ?: "")
        userPreference.setIsLoggedIn(true)
        userPreference.setIsSuperUser(userInfo.isSuperUser ?: false)
    }

    fun getUserInfoLocally(): User
         = User(email = userPreference.getUserEmail(),
            id = userPreference.getUserId(),
            userName = userPreference.getUserName(),
            mobile = userPreference.getMobileNumber(),
            isSuperUser = userPreference.getIsSuperUser())

    fun getIsLoggedInLocally(): Boolean = userPreference.getIsLoggedIn()

    fun getUserIdLocally(): String? = userPreference.getUserId()

    fun resetUserInfoLocally(){
        userPreference.setUserName("")
        userPreference.setUserId("")
        userPreference.setUserEmail("")
        userPreference.setMobileNumber("")
        userPreference.setMobileNumber("")
        userPreference.setIsLoggedIn(false)
        userPreference.setIsSuperUser(false)
    }

}