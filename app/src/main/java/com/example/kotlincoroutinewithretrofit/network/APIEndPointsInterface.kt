package com.example.kotlincoroutinewithretrofit.network

import com.example.kotlincoroutinewithretrofit.models.responsemodels.UserModel
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPointsInterface {

    @GET("users")
    suspend fun getUserList(
        @Query("page") page: Int
    ): UserModel
}