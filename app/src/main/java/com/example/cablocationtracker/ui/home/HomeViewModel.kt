package com.example.cablocationtracker.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {
    var _currentUser = MutableLiveData<User>()
    var _errorMessage = MutableLiveData<String>()
    val repo = Repository.instance

    fun getUserData(){
        val firbaseUser = repo.getCurrentUser()
        if(firbaseUser?.uid != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    _currentUser.postValue(repo.getUserInfo(firbaseUser.uid))

                }catch (e: Exception){
                    _errorMessage.postValue(e.message.toString())
                }
            }
        }
    }

    fun saveUserdataLocally(usr: User) {
        repo.saveUserInfoLocally(usr)
    }
}