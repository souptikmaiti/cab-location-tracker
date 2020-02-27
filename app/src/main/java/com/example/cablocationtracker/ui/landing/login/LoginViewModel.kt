package com.example.cablocationtracker.ui.landing.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cablocationtracker.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel(): ViewModel() {
    var _loginData = MutableLiveData<String>()

    fun doLogin(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Repository.instance.loginUser(email, password)
                _loginData.postValue("log in successful")
            }catch (e: Exception){
                _loginData.postValue(e.message.toString())
            }
        }
    }

}