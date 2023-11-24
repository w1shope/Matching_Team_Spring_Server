package com.example.matchingteam.activity

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.R
import com.example.matchingteam.activity.board.BoardActivity
import com.example.matchingteam.activity.login.LoginActivity
import com.example.matchingteam.activity.myinfo.MyInfoActivity
import com.example.matchingteam.activity.register.RegisterActivity
import com.example.matchingteam.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeLoginStatus()
        binding.buttonLogin.setOnClickListener {
            val isLogin: Boolean = isLogin()
            // 로그인 상태 -> 로그아웃 처리
            if (isLogin) {
                logout()
                changeLoginStatus()
            }
            // 로그아웃 상태 -> 로그인 화면으로 이동
            else {
                val intent: Intent = Intent(this@HomeActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        binding.buttonRegister.setOnClickListener {
            val intent: Intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.buttonProject.setOnClickListener {
            val isLogin: Boolean = isLogin()
            // 팀 프로젝트 화면으로 이동
            if (isLogin) {
                val intent: Intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "로그인 후 사용가능합니다", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonMyinfo.setOnClickListener {
            val isLogin: Boolean = isLogin()
            // 사용자 정보 화면으로 이동
            if (isLogin) {
                val intent: Intent = Intent(this, MyInfoActivity::class.java)
                val sp: SharedPreferences =
                    getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
                val loginEmail = sp.getString("loginEmail", null)
                val loginPassword = sp.getString("loginPassword", null)
                intent.putExtra("loginEmail", loginEmail)
                intent.putExtra("loginPassword", loginPassword)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "로그인 후 사용가능합니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 앱 강제 종료시에 처리할 로직
     * 로그인 정보 지운다
     */
    override fun onDestroy() {
        super.onDestroy()
//        logout()
    }

    /**
     * 로그인 여부에 따라 UI에 보여주는 내용(로그인<->로그아웃) 변경
     */
    private fun changeLoginStatus() {
        val sp: SharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        // null : key에 대한 value가 없을 때, 반환할 값
        val loginEmail: String? = sp.getString("loginEmail", null)
        val loginPassword: String? = sp.getString("loginPassword", null)

        if (loginEmail != null && loginPassword != null) {
            val binding =
                ActivityHomeBinding.bind(findViewById(R.id.rootLayoutId)) // 기존의 ActivityMyInfoBinding 사용
            binding.buttonLogin.text = "로그아웃"
        } else {
            val binding = ActivityHomeBinding.bind(findViewById(R.id.rootLayoutId))
            binding.buttonLogin.text = "로그인"
        }
    }

    /**
     * 로그아웃 메서드
     */
    private fun logout() {
        val sp: SharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        val autoLoginEdit: SharedPreferences.Editor = sp.edit()
        autoLoginEdit.clear()
        autoLoginEdit.commit()
        Toast.makeText(applicationContext, "로그아웃이 완료되었습니다", Toast.LENGTH_SHORT).show()

        val intent: Intent = Intent(this@HomeActivity, HomeActivity::class.java)
        startActivity(intent)
    }

    /**
     * 로그인 상태인지 확인하는 메서드
     */
    private fun isLogin(): Boolean {
        val sp: SharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        val loginEmail: String? = sp.getString("loginEmail", null)
        val loginPassword: String? = sp.getString("loginPassword", null)
        if (loginEmail == null || loginPassword == null)
            return false
        return true
    }
}