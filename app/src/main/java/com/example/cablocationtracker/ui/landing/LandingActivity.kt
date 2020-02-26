package com.example.cablocationtracker.ui.landing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.local.UserPreference
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity() {
    lateinit var userPreference: UserPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        userPreference = UserPreference()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if(userPreference.getIsLoggedIn(this)){

        }else{
            transaction.replace(R.id.fragmentContainer, LoginFragment.newInstance()).commit()
        }
    }
}