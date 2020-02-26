package com.example.cablocationtracker.ui.landing.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class RegistrationViewModel(): ViewModel() {

    var _fetchReg = MutableLiveData("")
    var _addUser = MutableLiveData("")

    fun doRegister(email:String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                Repository.instance.registerUser(email, password)
                _fetchReg.postValue("Registration Successful")

            }catch (e: Exception){
                _fetchReg.postValue(e.message.toString())
            }
        }
    }

    fun addUser(user:User){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Repository.instance.addUser(user)
                _addUser.postValue("User Added Successfully")
            }catch (e: Exception){
                _addUser.postValue(e.message.toString())
            }
        }
    }
}