package com.example.cablocationtracker.ui.home.observer.adapter

import com.example.cablocationtracker.data.models.User

interface ItemClickListener {
    fun onItemClick(user:User)
}