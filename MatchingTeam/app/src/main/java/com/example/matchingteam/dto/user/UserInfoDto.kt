package com.example.matchingteam.dto.user

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("email") val email: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("studentNum") val studentNum: Int
)
