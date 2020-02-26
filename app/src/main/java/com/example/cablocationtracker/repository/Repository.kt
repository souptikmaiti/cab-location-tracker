package com.example.cablocationtracker.repository

import com.example.cablocationtracker.data.remote.FirebaseConnect

class Repository private constructor(){

    val firebaseConnect: FirebaseConnect
            get() = FirebaseConnect()


    companion object{
        val instance = Repository()
    }

    fun registerUser(email:String, password: String){
        firebaseConnect.registerUser(email, password)
    }
}