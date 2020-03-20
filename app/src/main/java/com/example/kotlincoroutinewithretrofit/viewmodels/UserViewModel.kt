package com.example.kotlincoroutinewithretrofit.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import com.example.kotlincoroutinewithretrofit.network.APIEndPointsInterface
import com.example.kotlincoroutinewithretrofit.network.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    private val mutableUserList = MutableLiveData<UserModel>()
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)


    fun fetchUsers(pageNo: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse = apiEndPointsInterface.getUserList(pageNo)
            setUsers(apiResponse)
        }
    }

    private suspend fun setUsers(userModel: UserModel) {
        withContext(Dispatchers.Main) {
            mutableUserList.value = userModel
        }
    }

    fun getUsers(): LiveData<UserModel> {
        return mutableUserList
    }
}