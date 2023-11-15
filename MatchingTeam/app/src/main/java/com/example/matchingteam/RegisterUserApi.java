package com.example.matchingteam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterUserApi {
    @POST("/api/users")
    Call<User> saveUser(@Body User user);
}
