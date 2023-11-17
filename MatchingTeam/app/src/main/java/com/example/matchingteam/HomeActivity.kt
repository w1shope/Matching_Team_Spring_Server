package com.example.matchingteam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchingteam.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}