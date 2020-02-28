package com.example.cablocationtracker.ui.home.observer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cablocationtracker.R
import com.example.cablocationtracker.ui.base.BaseFragment
import com.example.cablocationtracker.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_observer.*

class ObserverFragment : BaseFragment() {

    lateinit var homeActivity: HomeActivity
    companion object {
        @JvmStatic
        fun newInstance(): ObserverFragment {
            return ObserverFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_observer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity = activity as HomeActivity
        tv_name.text = "Welcome " + homeActivity.currentUser?.userName
    }
}
