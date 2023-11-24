package com.example.matchingteam.api.user

import com.example.matchingteam.dto.user.UserInfoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyInfoApi {

    @GET("/api/users/tmp")
    fun getUserInfo(@Query("email") email: String): Call<UserInfoDto>
}