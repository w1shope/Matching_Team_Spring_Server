package com.example.matchingteam.dto.board

import com.google.gson.annotations.SerializedName

data class BoardStatusDto(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)