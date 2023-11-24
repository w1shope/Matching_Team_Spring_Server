package com.example.matchingteam.api.board

import com.example.matchingteam.domain.board.Board
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import java.sql.Timestamp

interface BoardApi {
    @GET("/api/boards")
    fun boards(): Call<List<Board>?>

    @GET("/api/boards/read")
    fun findBoard(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("createdDate") createdDate: Timestamp, // Assuming this needs to be a String
        @Query("viewCnt") viewCnt: Int
    ): Call<Board>

    @DELETE("/api/boards")
    fun deleteBoard(
        @Query("title") title: String,
        @Query("content") content: String,
        @Query("email") email: String
    ): Call<Boolean>
}