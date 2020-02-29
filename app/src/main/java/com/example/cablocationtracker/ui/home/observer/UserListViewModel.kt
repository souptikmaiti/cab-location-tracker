package com.example.cablocationtracker.ui.home.observer

import androidx.lifecycle.MutableLiveData
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.repository.Repository
import com.example.cablocationtracker.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserListViewModel: BaseViewModel() {
    var _userList = MutableLiveData<List<User>>()
    var _errorData = MutableLiveData<String>()
    val repo = Repository.instance
    var compositeDisposable = CompositeDisposable()

    fun getUsers(){
        val myId = repo.getCurrentUser()?.uid
        val disposable = repo.getUserList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _userList.postValue(it.filter { user -> user.id != myId })
            },
                {
                    _errorData.postValue(it.message.toString())
                })
            compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}