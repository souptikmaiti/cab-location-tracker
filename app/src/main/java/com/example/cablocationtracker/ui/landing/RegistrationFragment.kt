package com.example.cablocationtracker.ui.landing


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.cablocationtracker.R

class RegistrationFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance(): RegistrationFragment {
            return RegistrationFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        init()
    }

    private fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initViewModel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
