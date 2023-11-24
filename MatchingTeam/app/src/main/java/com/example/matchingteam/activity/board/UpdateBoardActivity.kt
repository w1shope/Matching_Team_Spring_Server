package com.example.matchingteam.activity.board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.R
import com.example.matchingteam.databinding.ActivityUpdateBoardBinding

class UpdateBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}