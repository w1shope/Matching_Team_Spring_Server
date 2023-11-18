package com.example.matchingteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.matchingteam.databinding.ActivityMyInfoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyInfoActivity : AppCompatActivity() {
    var userEmail: String? = null
    var userName: String? = null
    var userDepartment: String? = null
    var userStudentNum: Int = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserInfo("1923963@donga.ac.kr")

    }

    private fun getUserInfo(email: String) {
        val retrofit = RetrofitConnection.getInstance()
        val api: MyInfoApi = retrofit.create(MyInfoApi::class.java)
        val call: Call<UserInfoDto> = api.getUserInfo(email)
        call.enqueue(object: Callback<UserInfoDto> {
            override fun onResponse(call: Call<UserInfoDto>, response: Response<UserInfoDto>) {
                if(response.isSuccessful) {
                    if(response.body() != null) {
                        val userInfoDto: UserInfoDto? = response.body()
                        if(userInfoDto != null) {
                            Log.d("response : ",userInfoDto.toString())
                            userEmail = userInfoDto.email
                            userName = userInfoDto.name
                            userDepartment = userInfoDto.department
                            userStudentNum = userInfoDto.studentNum
                            updateUI(userEmail, userName, userDepartment, userStudentNum)
                        }
                    }
                    // 사용자 정보를 가져올 수 없으면 로그인창으로 이동
                    else {
                        Toast.makeText(applicationContext, "사용자 정보를 가져올 수 없습니다", Toast.LENGTH_LONG).show()
                        Handler().postDelayed({
                            val intent: Intent = Intent(this@MyInfoActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }, 1500)
                    }
                }
            }

            override fun onFailure(call: Call<UserInfoDto>, t: Throwable) {
                Toast.makeText(applicationContext, "네트워크에 문제가 발생하였습니다", Toast.LENGTH_LONG).show()
                Handler().postDelayed({
                    val intent: Intent = Intent(this@MyInfoActivity, LoginActivity::class.java)
                }, 1500)
            }
        })
    }
    private fun updateUI(email: String?, name: String?, department: String?, studentNum: Int) {
//        val binding = ActivityMyInfoBinding.inflate(layoutInflater) // 새로운 ActivityMyInfoBinding를 생성
        val binding = ActivityMyInfoBinding.bind(findViewById(R.id.rootLayoutId)) // 기존의 ActivityMyInfoBinding 사용
        binding.textViewName.setText(name)
        binding.textViewStudentNum.setText("동아대 ${studentNum.toString().substring(0, 2)}학번")
        binding.textViewDepartment1.setText(department)
        binding.textViewEmail.setText(email)
        binding.textViewDepartment2.setText(department)
    }

}