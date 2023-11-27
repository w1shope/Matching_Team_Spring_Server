package com.example.matchingteam.dto.board.comment

import com.google.gson.annotations.SerializedName

data class EnrolCommentDto(
    @SerializedName("email") val email: String,
    @SerializedName("commentContent") val commentContent: String,
    @SerializedName("boardTitle") val boardTitle: String,
    @SerializedName("boardContent") val boardContent: String
)