package com.example.cablocationtracker.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.base.BaseActivity
import com.example.cablocationtracker.ui.home.emitter.EmitterFragment
import com.example.cablocationtracker.ui.home.observer.ObserverFragment
import com.example.cablocationtracker.ui.home.observer.UserListFragment


class HomeActivity : BaseActivity() {

    lateinit var homeViewModel: HomeViewModel
    var currentUser: User ?= null
    var targetUser: User ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
        init()
        //showFragment()
    }

    private fun init() {
        homeViewModel.getUserData()
        this.showProgressDialog("Loading User Data...")
    }

    private fun initViewModel() {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel._currentUser.observe(this, Observer {
            setUser(it)
        })
    }

    private fun setUser(usr: User){
        this.hideProgressDialog()
        currentUser = usr
        homeViewModel.saveUserdataLocally(usr)
        val isSuperuser = usr.isSuperUser ?: false
        if(isSuperuser){
            showFragment(UserListFragment.newInstance(), false, false)
        }else{
            showFragment(EmitterFragment.newInstance(), true, false)
        }
    }

    fun modifyTargetUser(targetUsr: User){
        targetUser = targetUsr
    }
}
