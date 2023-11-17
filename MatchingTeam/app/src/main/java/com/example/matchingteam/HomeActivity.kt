package com.example.matchingteam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.matchingteam.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 2초후 로그인 화면으로 이동
        Handler().postDelayed (
            {
                val intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }, 1000
        )

    }
}