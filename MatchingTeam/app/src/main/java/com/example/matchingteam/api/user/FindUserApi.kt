package com.example.matchingteam.api.user

import com.example.matchingteam.dto.user.FindUserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FindUserApi {
    @GET("/api/users/{email}")
    fun findByEmail(
        @Path("email") email: String
    ): Call<FindUserDto>
}