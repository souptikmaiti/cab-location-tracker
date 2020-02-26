package com.example.cablocationtracker.repository

import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.data.remote.FirebaseConnect

class Repository private constructor(){

    val firebaseConnect: FirebaseConnect
            get() = FirebaseConnect()


    companion object{
        val instance = Repository()
    }

    suspend fun registerUser(email:String, password: String) = firebaseConnect.registerUser(email, password)

    suspend fun loginUser(email:String, password: String) = firebaseConnect.loginUser(email, password)

    suspend fun addUser(user: User) = firebaseConnect.addUser(user)

}