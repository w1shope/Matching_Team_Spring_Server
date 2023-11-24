package com.example.matchingteam.dto.board

import com.google.gson.annotations.SerializedName

data class EnrolBoardDto(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("email") val email: String
)