package com.example.cablocationtracker.ui.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.local.UserPreference
import com.example.cablocationtracker.ui.landing.login.LoginFragment

class LandingActivity : AppCompatActivity() {
    lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        userPreference = UserPreference()

        if(userPreference.getIsLoggedIn(this)){

        }else{
            showFragment(LoginFragment.newInstance())
        }
    }

    fun showFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment).commit()
    }
}