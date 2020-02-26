package com.example.cablocationtracker.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseConnect {

    private val USER_REF: String = "users"

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val fireStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    suspend fun loginUser(email: String, password: String): FirebaseUser?{
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return firebaseAuth.currentUser ?: throw FirebaseAuthException("", "")
    }

    suspend fun registerUser(email: String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }
}