package com.example.matchingteam.connection

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {

    // 객체를 하나만 생성하는 싱글톤 패턴을 적용합니다.
    companion object {
        // API 서버의 주소가 BASE_URL이 됩니다.
        private const val BASE_URL = "https://api.airvisual.com/v2/"
        private var INSTANCE: Retrofit? = null

        var gson = GsonBuilder().setLenient().create()
        fun getInstance(): Retrofit {
            OkHttpClient.Builder().retryOnConnectionFailure(false)
            if (INSTANCE == null) {  // null인 경우에만 생성
                INSTANCE = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080")  // API 베이스 URL 설정
                    .addConverterFactory(GsonConverterFactory.create(gson)) // 1)
                    .build()

            }
            return INSTANCE!!
        }
    }
}