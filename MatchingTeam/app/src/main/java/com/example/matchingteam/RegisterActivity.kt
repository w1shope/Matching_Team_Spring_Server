package com.example.matchingteam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.databinding.RegisterBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth
        val binding = RegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val registerBtn = binding.buttonRegisterUser
        registerBtn.setOnClickListener {
            // EditText -> 반환타입 : EditText이므로, toString()을 하여 String 타입으로 변환
            createAccount(binding.editTextRegisterEmail.text.toString(), binding.editTextRegisterPassword.text.toString())
        }
    }

    private fun createAccount(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this, "계정 생성 완료.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish() // 가입창 종료
                    } else {
                        Toast.makeText(
                            this, "계정 생성 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}