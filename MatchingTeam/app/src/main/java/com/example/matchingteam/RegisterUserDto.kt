package com.example.matchingteam

import com.google.gson.annotations.SerializedName

data class RegisterUserDto(
    @SerializedName("email") val email: String,
    @SerializedName("name")  val name: String,
    @SerializedName("password") val password: String,
    @SerializedName("department") val department: String,
    @SerializedName("development") val development: String
)
