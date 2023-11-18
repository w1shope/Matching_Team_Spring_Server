package com.example.matchingteam

import com.google.gson.annotations.SerializedName

data class LoginUserDto(
    @SerializedName("email") val email: String, @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("department") val department: String,
    @SerializedName("studentNum") val studentNum: Int)
