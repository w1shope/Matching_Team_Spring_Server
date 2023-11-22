package com.example.matchingteam.register

import com.google.gson.annotations.SerializedName

data class RegisterUserDto(
    @SerializedName("email") val email: String,
    @SerializedName("name")  val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("studentNum") val studentNum: Int,
    @SerializedName("department") val department: String,
    @SerializedName("development") val development: String
)
