package com.example.cablocationtracker.ui.home.observer

import androidx.lifecycle.MutableLiveData
import com.example.cablocationtracker.data.models.SmallLocation
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.repository.Repository
import com.example.cablocationtracker.ui.base.BaseViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ObserverViewModel: BaseViewModel() {
    var _locationData = MutableLiveData<SmallLocation>()
    var _errorData = MutableLiveData<String>()
    val repo = Repository.instance
    var compositeDisposable = CompositeDisposable()

    fun getLocation(uid: String) {
        val disposable = repo.getLocation(uid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                    _locationData.postValue(it)
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