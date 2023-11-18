package com.example.matchingteam

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyInfoApi {

    @GET("/api/users/tmp")
    fun getUserInfo(@Query("email") email: String): Call<UserInfoDto>
}