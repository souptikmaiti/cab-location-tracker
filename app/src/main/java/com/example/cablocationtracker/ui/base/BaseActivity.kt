package com.example.cablocationtracker.ui.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.cablocationtracker.R
import com.example.cablocationtracker.util.NetworkUtil

open class BaseActivity: AppCompatActivity() {
    var currentFragment: Fragment? = null
    lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        progressDialog = getProgressDialog(this)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    private fun getProgressDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        val inflate = LayoutInflater.from(context).inflate(R.layout.progress_layout, null)
        dialog.setContentView(inflate)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    fun showProgressDialog(message: String) {
        try {
            hideProgressDialog()
            val tvTitle= progressDialog.findViewById<TextView>(R.id.tv_progress_title)
            tvTitle.text = message
            progressDialog.setCancelable(false)
            progressDialog.show()
        } catch (ex: Exception) {
            Log.e("TAG", "progress dialog error")
        }
    }

    fun hideProgressDialog(){
        try{
            if(progressDialog.isShowing)
                progressDialog.dismiss()
        }
        catch (ex: Exception){

        }
    }

    fun showAlert(message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setPositiveButton("OK") { dialog, arg1 -> dialog.dismiss() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    fun isNetworkAvailable():Boolean{
        if(NetworkUtil.isNetworkAvailable(this)){
            return true
        }
        else{
            showAlert("No Internet Connection")
            return false
        }
    }

    fun showFragment(contentFragment: Fragment, clearBackStack: Boolean, addToBackStack: Boolean){
        currentFragment = contentFragment
        val fragmentManager = supportFragmentManager
        if (clearBackStack) {
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (addToBackStack) {
            fragmentTransaction.add(R.id.fragmentContainer, contentFragment, null)
        } else {
            fragmentTransaction.replace(R.id.fragmentContainer, contentFragment)
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}