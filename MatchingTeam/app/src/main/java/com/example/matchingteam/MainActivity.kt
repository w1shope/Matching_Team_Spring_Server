package com.example.matchingteam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textViewResult = binding.textViewResult
        var retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val jsonPlaceHolderApi = retrofit.create(RestApi::class.java)
        val call: Call<List<User>> = jsonPlaceHolderApi.getPost()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (!response.isSuccessful) {
                    textViewResult.text = "Code: ${response.code()}"
                    return
                }
                val users: List<User>? = response.body()
                users?.forEach { user ->
                    var content = ""
                    content += "ID: ${user.id}\n"
                    content += "Email: ${user.email}\n"
                    content += "Name: ${user.name}\n"
                    content += "Password: ${user.password}\n"
                    content += "Development: ${user.development}\n"
                    content += "Introduce: ${user.introduce}\n\n"
                    textViewResult.append(content)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                textViewResult.setText(t.message)
            }
        })
    }
}