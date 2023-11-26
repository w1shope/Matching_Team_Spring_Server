package com.example.matchingteam.domain.board

import com.google.gson.annotations.SerializedName
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
    var createdDate: Timestamp,
    @SerializedName("statusId")
    var statusId: Int
)