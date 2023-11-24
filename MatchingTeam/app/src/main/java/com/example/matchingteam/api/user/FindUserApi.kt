package com.example.matchingteam.api.user

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
}