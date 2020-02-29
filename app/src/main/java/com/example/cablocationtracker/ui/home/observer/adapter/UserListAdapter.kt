package com.example.cablocationtracker.ui.home.observer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.User
import kotlinx.android.synthetic.main.user_card.view.*

class UserListAdapter(val userList: List<User>, val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    = UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: User){
            itemView.tv_username.text = user.userName
            itemView.tv_mobile.text = user.mobile
            itemView.card_user.setOnClickListener {
                itemClickListener?.onItemClick(user)
            }
        }
    }
}