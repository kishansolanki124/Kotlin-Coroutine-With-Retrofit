package com.example.kotlincoroutinewithretrofit.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
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

        //todo for recyclerview without nested scrollview
//        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(
//                recyclerView: RecyclerView,
//                dx: Int, dy: Int
//            ) {
//                super.onScrolled(recyclerView, dx, dy)
//                val totalItemCount = layoutManager.itemCount
//                val lastVisibleItem = layoutManager
//                    .findLastVisibleItemPosition()
//                if (!loading
//                    && totalItemCount <= lastVisibleItem + visibleThreshold
//                ) {
//                    page += 1
//                    loading = true
//                    recyclerView.post {
//                        usersAdapter.addLoadingFooter()
//                    }
//                    println("new page loaded: $page")
//                    userViewModel.fetchUsersPagination(page)
//                }
//            }
//        })

        //todo for recyclerview inside nested scrollview
        nscMain.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItem = layoutManager
                .findLastVisibleItemPosition()

            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1)
                        .measuredHeight - v.measuredHeight &&
                    scrollY > oldScrollY
                    &&
                    !loading
                    && totalItemCount <= lastVisibleItem + visibleThreshold
                ) {
                    //code to fetch more data for endless scrolling
                    page += 1
                    loading = true
                    v.getChildAt(v.childCount - 1).post {
                        usersAdapter.addLoadingFooter()
                    }
                    println("new page loaded: $page")
                    userViewModel.fetchUsersPagination(page)

                }
            }
        }
    }
}