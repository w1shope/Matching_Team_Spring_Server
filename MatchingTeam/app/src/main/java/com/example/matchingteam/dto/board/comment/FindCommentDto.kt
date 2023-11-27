package com.example.matchingteam.dto.board.comment

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class FindCommentDto(
    @SerializedName("content") val content: String,
    @SerializedName("name") val name: String,
    @SerializedName("createdDate") val createdDate: Timestamp
)
