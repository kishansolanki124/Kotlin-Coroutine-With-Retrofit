package com.example.kotlincoroutinewithretrofit.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlincoroutinewithretrofit.R
import com.example.kotlincoroutinewithretrofit.adapters.AnimalAdapter
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import com.example.kotlincoroutinewithretrofit.utils.isConnected
import com.example.kotlincoroutinewithretrofit.utils.setRecyclerViewLayoutManager
import com.example.kotlincoroutinewithretrofit.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    private lateinit var animalAdapter: AnimalAdapter
    private var usersList: ArrayList<UserModel.Data> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        if (isConnected(this))
            userViewModel.fetchUsers(1)

        userViewModel.getUsers().observe(this, Observer { userViewModel ->
            animalAdapter.addAll(userViewModel.data)
        })

        setupUserRecyclerView()
    }

    private fun setupUserRecyclerView() {
        setRecyclerViewLayoutManager(rvUsers, this)
        animalAdapter = AnimalAdapter(usersList, this@MainActivity)
        rvUsers.adapter = animalAdapter
    }
}