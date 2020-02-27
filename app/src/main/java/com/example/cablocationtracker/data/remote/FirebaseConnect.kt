package com.example.cablocationtracker.data.remote

import android.location.Location
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.util.common.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseConnect {

    private val USER_REF: String = "users"
    private val LOCATION_REF: String = "locations"

    private val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val fireStore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

    fun signOut() = firebaseAuth.signOut()

    suspend fun getCurrentUserInfo(uid: String): User? {
        val userInfo = fireStore.collection(USER_REF).document(uid).get().await()
        return userInfo.toObject(User::class.java)
    }

    suspend fun loginUser(email: String, password: String){
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    suspend fun registerUser(email: String, password:String){
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    suspend fun addUser(user:User){
        val uid = getCurrentUser()?.uid
        val email = getCurrentUser()?.email
        if(uid != null){
            user.id = uid
            user.email = email
            fireStore.collection(USER_REF).document(uid).set(user).await()
        }
    }

    suspend fun updateLocation(smallLocation: SmallLocation){
        val uid = getCurrentUser()?.uid
        if(uid != null){
            fireStore.collection(LOCATION_REF).document(uid).set(smallLocation, SetOptions.merge()).await()
        }
    }

    suspend fun getLocation(uid:String): SmallLocation? {
        val locationInfo = fireStore.collection(LOCATION_REF).document(uid).get().await()
        return locationInfo.toObject(SmallLocation::class.java)
    }
}