package com.example.kotlincoroutinewithretrofit.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincoroutinewithretrofit.R
import com.example.kotlincoroutinewithretrofit.adapters.UsersAdapter
import com.example.kotlincoroutinewithretrofit.models.responsemodels.DummyUserModel
import com.example.kotlincoroutinewithretrofit.utils.isConnected
import com.example.kotlincoroutinewithretrofit.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var usersAdapter: UsersAdapter
    private var loading = false
    private var visibleThreshold = 5
    private var page = 1

    private var usersList: ArrayList<DummyUserModel.Data?> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUserRecyclerView()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        if (isConnected(this)) {
            loading = true
            //userViewModel.fetchUsers(1)
            usersAdapter.addLoadingFooter()
            userViewModel.fetchUsersPagination(page)
        }

        userViewModel.getUsersPagination().observe(this, Observer { userViewModel ->
            usersAdapter.removeLoadingFooter()
            usersAdapter.addAll(userViewModel.data)
            loading = false

        })
//        userViewModel.getUsers().observe(this, Observer { userViewModel ->
//            usersAdapter.addAll(userViewModel.data)
//        })
    }

    private fun setupUserRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        rvUsers.layoutManager = layoutManager
        rvUsers.itemAnimator = DefaultItemAnimator()
        rvUsers.isNestedScrollingEnabled = true

        usersAdapter = UsersAdapter(usersList, this@MainActivity)
        rvUsers.adapter = usersAdapter

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager
                    .findLastVisibleItemPosition()
                if (!loading
                    && totalItemCount <= lastVisibleItem + visibleThreshold
                ) {
                    page += 1
                    loading = true
                    recyclerView.post {
                        usersAdapter.addLoadingFooter()
                    }
                    println("new page loaded: $page")
                    userViewModel.fetchUsersPagination(page)
                }
            }
        })
    }
}