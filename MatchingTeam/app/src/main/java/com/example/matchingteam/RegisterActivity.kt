package com.example.matchingteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.matchingteam.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    lateinit var textView: TextView
    val development: Array<String> = arrayOf("백엔드", "프론트엔드", "데이터분석", "프로젝트 기획", "디자인")
    var department: Array<String> = arrayOf(
        "한국어문학과", "영어영문학과", "고고미술사학과", "교육학과", "경제학과", "미디어커뮤니케이션학과", "경영학과", "무역학과", "회계학과",
        "관광경영학과", "경영정보학과", "수학과", "화학과", "식품영양학과", "의상섬유학과", "식품생명공학과", "응용생명과학과", "건강과학과",
        "중개의과학과", "건축학과", "토목공학과", "기계공학과", "화학공학과", "전기공학과", "환경공학과", "금속공학과", "전자공학과", "조경학과",
        "도시계획학과", "조선해양플랜트공학과", "체육학과", "태권도학과", "미술학과", "음악학과", "조형디자인학과", "의학과", "국제법무학과", "예술학과",
        "기업정책학과", "스포츠의학과", "ICT 융합해양스마트시티공학과"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 개발 희망 분야 선택
//        textView = binding.textViewDropDown
        val developmentSpinner = binding.spinnerDevelopment
        val developmentAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, development)
        developmentAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        developmentSpinner.setAdapter(developmentAdapter)

        var developmentSelectedItem: String = "백엔드"
        developmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                developmentSelectedItem = development[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // 학과 선택
        val departmentSpinner = binding.spinnerDepartmentDropDown
        val departmentAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, department)
        departmentAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        departmentSpinner.setAdapter(departmentAdapter)

        var departmentSelectedItem: String = "한국어문학과"
        departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                departmentSelectedItem = department[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val registerBtn: Button = binding.buttonRegisterUser
        registerBtn.setOnClickListener {
            val email: String = binding.editTextRegisterEmail.text.toString()
            val name: String = binding.editTextRegisterName.text.toString()
            val password: String = binding.editTextRegisterPassword.text.toString()
            val department: String = departmentSelectedItem
            val development: String = developmentSelectedItem
            createUser(email, name, password, development)
        }
    }

    fun createUser(email: String, name: String, password: String, development: String){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val api: RegisterUserApi = retrofit.create(RegisterUserApi::class.java)
        val call: Call<User> = api.saveUser(User(email, name, password, development))
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}