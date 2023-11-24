package com.example.matchingteam.dto.board

import com.google.gson.annotations.SerializedName

data class UpdateBoardDto(
    @SerializedName("oldTitle") val oldTitle: String,
    @SerializedName("oldContent") val oldContent: String,
    @SerializedName("newTitle") val newTitle: String,
    @SerializedName("newContent") val newContent: String
)
