package com.example.cablocationtracker.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.base.BaseActivity
import com.example.cablocationtracker.ui.home.HomeActivity
import com.example.cablocationtracker.ui.landing.LandingActivity
import com.example.cablocationtracker.util.Toaster

class SplashActivity : BaseActivity() {
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViewModel()
        init()
        splashViewModel.checkLocalData()
    }

    private fun init() {

    }

    private fun initViewModel() {
        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        splashViewModel._localUser.observe(this, Observer {
            navigateToHome(it)
        })
        splashViewModel._errorMsg.observe(this, Observer {
            navigateToLogin(it)
        })
    }

    private fun navigateToLogin(str: String?) {
        //Toaster.showShort(this, str ?: "")
        this.showProgressDialog("Loading...")
        startActivity(Intent(this, LandingActivity::class.java))
        finish()
    }

    private fun navigateToHome(u: User) {
        this.showProgressDialog("Loading...")
        if(!u.id.isNullOrBlank()){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }else{
            navigateToLogin("user id null")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.hideProgressDialog()
    }
}
