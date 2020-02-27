package com.example.cablocationtracker.util

import android.content.Context
import android.graphics.PorterDuff
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.cablocationtracker.R

object Toaster {
    fun showLong(context: Context, text: CharSequence){
        val toast = android.widget.Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorToaster), PorterDuff.Mode.SRC_IN
        )
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        toast.show()
    }

    fun showShort(context: Context, text: CharSequence){
        val toast = android.widget.Toast.makeText(context, text, Toast.LENGTH_SHORT)
        toast.view.background.setColorFilter(
            ContextCompat.getColor(context, R.color.colorToaster), PorterDuff.Mode.SRC_IN
        )
        val textView = toast.view.findViewById(android.R.id.message) as TextView
        textView.setTextColor(ContextCompat.getColor(context, R.color.black))
        toast.show()
    }

}