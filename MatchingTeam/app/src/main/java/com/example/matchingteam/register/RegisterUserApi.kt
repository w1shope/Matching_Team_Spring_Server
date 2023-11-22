package com.example.matchingteam.register

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RegisterUserApi {
    @POST("/api/users")
    fun saveUser(@Body registerUserDto: RegisterUserDto): Call<RegisterUserDto>

    @GET("/api/authenticate")
    fun userEmailAuthentication(@Query("address") address: String): Call<String>

    @POST("/api/authenticate")
    fun userEmailAuthenticationConfirm(@Body authenticateCode: String?): Call<Boolean>
}