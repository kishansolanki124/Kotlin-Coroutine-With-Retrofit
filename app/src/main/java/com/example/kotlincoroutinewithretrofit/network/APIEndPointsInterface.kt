package com.example.kotlincoroutinewithretrofit.network

import com.example.kotlincoroutinewithretrofit.models.responsemodels.DummyUserModel
import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPointsInterface {

    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): UserModel

    //https://gorest.co.in/public-api/users?page=2
    @GET("users")
    suspend fun getUserListPagination(
        @Query("page") page: Int
    ): DummyUserModel
}