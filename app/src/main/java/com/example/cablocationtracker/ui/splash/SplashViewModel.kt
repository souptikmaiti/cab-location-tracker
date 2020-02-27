package com.example.cablocationtracker.ui.splash

import androidx.lifecycle.MutableLiveData
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.repository.Repository
import com.example.cablocationtracker.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class SplashViewModel: BaseViewModel() {
    var _localUser = MutableLiveData<User>()
    var _errorMsg = MutableLiveData<String>()
    val repo = Repository.instance

    fun checkLocalData(){
        if(repo.getIsLoggedInLocally()){
            val currUser = repo.getCurrentUser()
            if(currUser != null && currUser.uid == repo.getUserIdLocally()){
                CoroutineScope(Dispatchers.IO).launch{
                    try {
                        _localUser.postValue(repo.getUserInfo(currUser.uid))
                    }catch (e: Exception){
                        _errorMsg.postValue(e.message.toString())
                    }
                }
            }
        }else{
            _errorMsg.postValue("Not Logged in")
        }
    }


}