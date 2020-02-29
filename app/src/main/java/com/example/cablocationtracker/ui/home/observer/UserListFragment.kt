package com.example.cablocationtracker.ui.home.observer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.cablocationtracker.R
import com.example.cablocationtracker.data.models.User
import com.example.cablocationtracker.ui.home.HomeActivity
import com.example.cablocationtracker.ui.home.observer.adapter.ItemClickListener
import com.example.cablocationtracker.ui.home.observer.adapter.UserListAdapter
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : Fragment(), ItemClickListener {

    companion object{
        @JvmStatic
        fun newInstance(): UserListFragment{
            return UserListFragment()
        }
    }

    lateinit var userListViewModel: UserListViewModel
    lateinit var homeActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeActivity = activity as HomeActivity
        intViewModel()
        init()
        userListViewModel.getUsers()
    }

    private fun init() {

    }

    private fun intViewModel() {
        userListViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)
        userListViewModel._userList.observe(viewLifecycleOwner, Observer {
            populateUserList(it)
        })
    }

    private fun populateUserList(userList: List<User>?) {
        userList?.let {
            rv_emitters.layoutManager = LinearLayoutManager(context)
            rv_emitters.adapter = UserListAdapter(userList, this)
        }
    }

    override fun onItemClick(user: User) {
        homeActivity.modifyTargetUser(user)
        homeActivity.showFragment(ObserverFragment.newInstance(), false, false)
    }

}
