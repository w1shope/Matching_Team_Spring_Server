package com.example.matchingteam.board

import retrofit2.Call
import retrofit2.http.GET

interface BoardApi {
    @GET("/api/boards")
    fun boards(): Call<List<Board>?>

}