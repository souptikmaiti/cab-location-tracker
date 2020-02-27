package com.example.cablocationtracker.ui.landing.registration


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.base.BaseActivity
import com.example.cablocationtracker.ui.base.BaseFragment
import com.example.cablocationtracker.ui.landing.LandingActivity
import com.example.cablocationtracker.ui.landing.login.LoginFragment
import com.example.cablocationtracker.util.Toaster
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationFragment : BaseFragment() {

    lateinit var viewModel: RegistrationViewModel
    lateinit var landingActivity: LandingActivity
    var email: String = ""
    var password: String = ""

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
        landingActivity = activity as LandingActivity
        initViewModel()
        init()
    }

    private fun init() {
        btn_register.setOnClickListener {
            doRegisterAndAddUser()
        }

        tv_login.setOnClickListener {
            landingActivity.showFragment(LoginFragment.newInstance(), true, false)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        viewModel._fetchReg.observe(viewLifecycleOwner, Observer { setRegistration(it) })
        viewModel._addUser.observe(viewLifecycleOwner, Observer { setAddUser(it) })
    }

    private fun doRegisterAndAddUser(){
        email = et_email.text.toString()
        password = et_password.text.toString()
        if(!email.isNullOrBlank() && !password.isNullOrBlank()){
            landingActivity.showProgressDialog("Registration in Progress...")
            viewModel.doRegister(et_email.text.toString(), et_password.text.toString())
        }

    }

    private fun setRegistration(status: String){
        landingActivity.hideProgressDialog()
        if(status.equals("Registration Successful")){
            Toaster.showShort(context!!,"Registration Successful")
            val user = User(email = email, id = null, isSuperUser = false, mobile = et_mobile.text.toString()
            ,userName = et_username.text.toString())
            landingActivity.showProgressDialog("Creating user...")
            viewModel.addUser(user)
        }else{
            Toaster.showShort(context!!, status)
        }
    }

    private fun setAddUser(status: String){
        landingActivity.hideProgressDialog()
        if(status.equals("User Added Successfully")){
            Toaster.showShort(context!!, "User Added Successfully")
            landingActivity.navigateToHome()
        }else{
            Toaster.showShort(context!!, status)
        }
    }

}
