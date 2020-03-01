package com.example.cablocationtracker.ui.landing.login


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.cablocationtracker.R
import com.example.cablocationtracker.ui.base.BaseFragment
import com.example.cablocationtracker.ui.landing.LandingActivity
import com.example.cablocationtracker.ui.landing.registration.RegistrationFragment
import com.example.cablocationtracker.util.Toaster
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    lateinit var loginViewModel: LoginViewModel
    lateinit var landingActivity: LandingActivity
    companion object {
        @JvmStatic
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        landingActivity = activity as LandingActivity
        initViewModel()
        init()
    }

    private fun init() {

        tv_register.setOnClickListener {
            landingActivity.showFragment(RegistrationFragment.newInstance(), true, false)
        }

        btn_login.setOnClickListener {
            doLogin()
        }
    }

    private fun initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginViewModel._loginData.observe(viewLifecycleOwner, Observer {
            setLogin(it)
        })
    }

    private fun doLogin() {
        val email = et_email.text.toString()
        val password = et_password.text.toString()
        if(!email.isNullOrBlank() && !password.isNullOrBlank()){
            landingActivity.showProgressDialog("logging in...")
            loginViewModel.doLogin(email, password)
        }
    }

    private fun setLogin(status: String?) {
        landingActivity.hideProgressDialog()
        if(status.equals("log in successful")){
            Toaster.showShort(context!!, status ?: "log in successful")
            landingActivity.navigateToHome()
        }else{
            Toaster.showShort(context!!,status ?: "log in failed")
        }
    }

}
