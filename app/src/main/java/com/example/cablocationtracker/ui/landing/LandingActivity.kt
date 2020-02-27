package com.example.cablocationtracker.ui.landing

import android.content.Intent
import android.os.Bundle
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.local.UserPreference
import com.example.cablocationtracker.ui.base.BaseActivity
import com.example.cablocationtracker.ui.home.HomeActivity
import com.example.cablocationtracker.ui.landing.login.LoginFragment

class LandingActivity : BaseActivity() {
    lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        userPreference = UserPreference()

        showFragment(LoginFragment.newInstance(), false, false)
    }

    fun navigateToHome(){
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}