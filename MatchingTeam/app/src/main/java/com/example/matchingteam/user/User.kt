package com.example.matchingteam.user

import com.google.gson.annotations.SerializedName
import retrofit2.http.FormUrlEncoded

class User {
    @SerializedName("email")
    private lateinit var email: String

    @SerializedName("name")
    private lateinit var name: String

    @SerializedName("password")
    private lateinit var password: String

    @SerializedName("department")
    private lateinit var department: String

    @SerializedName("development")
    private lateinit var development: String

    constructor(
        email: String,
        name: String,
        password: String,
        department: String,
        development: String
    ) {
        this.email = email
        this.name = name
        this.password = password
        this.department = department
        this.development = development
    }
}