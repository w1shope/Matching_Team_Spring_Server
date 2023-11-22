package com.example.matchingteam.myinfo

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.matchingteam.login.LoginActivity
import com.example.matchingteam.R
import com.example.matchingteam.connection.RetrofitConnection
import com.example.matchingteam.user.UserInfoDto
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
        // 이전 Activity에서 사용된 Intent 가져온다.
        val intent: Intent = getIntent()
        val loginEmail: String? = intent.getStringExtra("loginEmail")
        getUserInfo(loginEmail.toString())
        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    /**
     * 사용자 정보를 가져온다.
     */
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

    /**
     * getUserInfo()를 통해 가져온 사용자 정보를 화면에 뿌린다
     */
    private fun updateUI(email: String?, name: String?, department: String?, studentNum: Int) {
//        val binding = ActivityMyInfoBinding.inflate(layoutInflater) // 새로운 ActivityMyInfoBinding를 생성
        val binding = ActivityMyInfoBinding.bind(findViewById(R.id.rootLayoutId)) // 기존의 ActivityMyInfoBinding 사용
        binding.textViewName.setText(name)
        binding.textViewStudentNum.setText("동아대 ${studentNum.toString().substring(0, 2)}학번")
        binding.textViewDepartment1.setText(department)
        binding.textViewEmail.setText(email)
        binding.textViewDepartment2.setText(department)
    }

    /**
     * 로그아웃 메서드
     */
    private fun logout() {
        val sp: SharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        val autoLoginEdit: SharedPreferences.Editor = sp.edit()
        autoLoginEdit.clear()
        autoLoginEdit.commit()
        Toast.makeText(applicationContext, "로그아웃이 완료되었습니다", Toast.LENGTH_LONG).show()

        val intent: Intent = Intent(this@MyInfoActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}