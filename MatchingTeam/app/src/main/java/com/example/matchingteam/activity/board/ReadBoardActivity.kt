package com.example.matchingteam.activity.board

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.api.board.BoardApi
import com.example.matchingteam.api.user.FindUserApi
import com.example.matchingteam.connection.RetrofitConnection
import com.example.matchingteam.databinding.ActivityReadBoardBinding
import com.example.matchingteam.dto.user.FindUserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityReadBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val title = intent.getStringExtra("title")!!
        val content = intent.getStringExtra("content")!!
        updateUI(title, content)
        checkIfCurrentUserIsAuthor(title, content)

        binding.buttonBoardList.setOnClickListener {
            val intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            startActivity(intent)
        }

        binding.buttonUpdate.setOnClickListener {
            val intent = Intent(this, UpdateBoardActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            startActivity(intent)
        }

        binding.buttonDelete.setOnClickListener {
            deleteBoard(title, content, getLoginUserEmail())
        }
    }

    /**
     * 게시물 정보를 업데이트 하는 메서드
     */
    private fun updateUI(title: String, content: String) {
        binding.editTextTitle.setText(title)
        binding.editTextContent.setText(content)
    }

    /**
     * 게시물 작성자가 본인인지 확인하는 메서드
    -> 게시물 수정, 삭제 버튼 생성에 필요
     */
    private fun checkIfCurrentUserIsAuthor(title: String, content: String) {
        val retrofit = RetrofitConnection.getInstance()
        val api: FindUserApi = retrofit.create(FindUserApi::class.java)
        val call: Call<Boolean> = api.isWriter(getLoginUserEmail(), title, content)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val isWriter: Boolean = response.body()!!
                        Log.d("isWriter=", isWriter.toString())
                        if (isWriter) {
                            binding.buttonUpdate.visibility = View.VISIBLE
                            binding.buttonDelete.visibility = View.VISIBLE
                        }
//                        else {
//                            binding.buttonUpdate.visibility = View.GONE
//                            binding.buttonDelete.visibility = View.GONE
//                        }
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
            }
        })
    }

    /**
     * 게시물 삭제
     */
    private fun deleteBoard(title: String, content: String, loginEmail: String) {
        val retrofit = RetrofitConnection.getInstance()
        val api: BoardApi = retrofit.create(BoardApi::class.java)
        val call: Call<Boolean> = api.deleteBoard(title, content, loginEmail)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    if (response.body()?.equals(true)!!) {
                        Toast.makeText(applicationContext, "게시물이 삭제되었습니다", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@ReadBoardActivity, BoardActivity::class.java)
                            startActivity(intent)
                        }, 1500)
                    }
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, "네트워크에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getLoginUserEmail(): String {
        val sp: SharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        return sp.getString("loginEmail", null)!!
    }
}