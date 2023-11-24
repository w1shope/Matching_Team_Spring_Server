package com.example.matchingteam.dto.user

import com.google.gson.annotations.SerializedName

data class FindUserDto(
    @SerializedName("email") val email: String
)
