package com.example.matchingteam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLoginUser.setOnClickListener {
            val email: String = binding.editTextLoginEmail.text.toString()
            val password: String = binding.editTextLoginPassword.text.toString()
            checkLogin(email, password)
        }

        binding.textViewRegisterBtn.setOnClickListener {
            val intent: Intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLogin(email: String, password: String) {
        val retrofit = RetrofitConnection.getInstance()
        val api: LoginUserApi = retrofit.create(LoginUserApi::class.java)
        val call: Call<LoginUserDto> = api.findByUserEmailAndPassword(email, password)
        call.enqueue(object : Callback<LoginUserDto> {
            override fun onResponse(call: Call<LoginUserDto>, response: Response<LoginUserDto>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        Toast.makeText(applicationContext, "로그인에 성공하였습니다", Toast.LENGTH_LONG).show()
                        // Toast 메시지를 띄우고 1.5초 후에 finish() 호출한다.
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent: Intent = Intent(this@LoginActivity, MyInfoActivity::class.java)
                            startActivity(intent)
//                            finish()
                        }, 750)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "아이디 또는 비밀번호가 일치하지 않습니다",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "서버로부터 응답이 없습니다", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginUserDto>, t: Throwable) {
                Toast.makeText(applicationContext, "네트워크에 문제가 발생하였습니다", Toast.LENGTH_LONG).show()
            }
        })
    }
}