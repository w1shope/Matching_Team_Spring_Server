package com.example.matchingteam.activity.myinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matchingteam.R
import com.example.matchingteam.databinding.ActivityBoardBinding
import com.example.matchingteam.databinding.ActivityEnrolBoardBinding

class EnrolBoardListActivity : AppCompatActivity() {
    lateinit var binding: ActivityEnrolBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrolBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}