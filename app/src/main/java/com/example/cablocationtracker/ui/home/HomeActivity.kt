package com.example.cablocationtracker.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cablocationtracker.R
import com.example.cablocationtracker.ui.base.BaseActivity
import com.example.cablocationtracker.ui.home.emitter.EmitterFragment


class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        showFragment(EmitterFragment.newInstance(), false, false)
    }
}
