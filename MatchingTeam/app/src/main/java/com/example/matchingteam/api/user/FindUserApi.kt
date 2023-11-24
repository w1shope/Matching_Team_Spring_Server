package com.example.matchingteam.api.user

import com.example.matchingteam.dto.user.FindUserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FindUserApi {
    @GET("/api/boards/{email}")
    fun isWriter(
        @Path("email") email: String,
        @Query("title") title: String,
        @Query("content") content: String
    ): Call<Boolean>

    @GET("/api/users/{email}/{password}")
    fun userInfo(@Path("email") email: String, @Path("password") password: String): Call<FindUserDto>
}