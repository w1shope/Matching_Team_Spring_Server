package com.example.matchingteam.dto.user

import com.google.gson.annotations.SerializedName

//data class FindUserDto(
//    @SerializedName("email") val email: String
//)

data class FindUserDto(
    @SerializedName("writeCount") val writeCount: Int,
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("projectCount") val projectCount: Int
)