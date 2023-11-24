package com.example.matchingteam.api.board

import com.example.matchingteam.dto.board.EnrolBoardDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EnrolBoardApi {
    @POST("/api/boards")
    fun enrolBoard(@Body dto: EnrolBoardDto): Call<Boolean>
}