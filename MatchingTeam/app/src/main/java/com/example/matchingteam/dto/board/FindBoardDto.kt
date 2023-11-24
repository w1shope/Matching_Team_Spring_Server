package com.example.matchingteam.dto.board

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class FindBoardDto(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("createdDate") val createdDate: Timestamp,
    @SerializedName("viewCnt") val viewCnt: Int
)
