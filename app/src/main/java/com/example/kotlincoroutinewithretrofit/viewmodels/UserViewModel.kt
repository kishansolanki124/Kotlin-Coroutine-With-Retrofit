package com.example.kotlincoroutinewithretrofit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutinewithretrofit.models.responsemodels.DummyUserModel
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import com.example.kotlincoroutinewithretrofit.network.APIEndPointsInterface
import com.example.kotlincoroutinewithretrofit.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    private val mutableUserList = MutableLiveData<UserModel>()
    private val mutableUserListPagination = MutableLiveData<DummyUserModel>()
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)


    /**
     * Dispatchers.IO for network or disk operations that takes longer time and runs in background thread
     */
    fun fetchUsers(pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = apiEndPointsInterface.getUserList(pageNo)
            setUsers(apiResponse)
        }
    }

    /**
     * Dispatchers.IO for network or disk operations that takes longer time and runs in background thread
     */
    fun fetchUsersPagination(pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = apiEndPointsInterface.getUserListPagination(pageNo)
            setUsers(apiResponse)
        }
    }

    /**
     * Dispatchers.Main for UI related stuff which runs on Main thread
     */
    private suspend fun setUsers(userModel: UserModel) {
        withContext(Dispatchers.Main) {
            mutableUserList.value = userModel
        }
    }

    /**
     * Dispatchers.Main for UI related stuff which runs on Main thread
     */
    private suspend fun setUsers(dummyUserModel: DummyUserModel) {
        withContext(Dispatchers.Main) {
            mutableUserListPagination.value = dummyUserModel
        }
    }

    fun getUsers(): LiveData<UserModel> {
        return mutableUserList
    }

    fun getUsersPagination(): LiveData<DummyUserModel> {
        return mutableUserListPagination
    }
}