package com.example.cablocationtracker.ui.home.observer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cablocationtracker.R
import com.example.cablocationtracker.ui.base.BaseFragment

class ObserverFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_observer, container, false)
    }
}
