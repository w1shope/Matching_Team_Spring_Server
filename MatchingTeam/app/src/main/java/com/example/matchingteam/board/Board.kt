package com.example.matchingteam.board

import com.google.gson.annotations.SerializedName
import java.io.Serial
import java.sql.Timestamp

data class Board(
    @SerializedName("id")
    var id: Long,
    @SerializedName("title")
    var title: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("viewCnt")
    var viewCnt: Int,
    @SerializedName("createdDate")
    var createdDate: Timestamp
)