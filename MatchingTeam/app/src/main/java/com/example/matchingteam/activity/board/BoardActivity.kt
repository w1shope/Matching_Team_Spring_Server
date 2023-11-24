package com.example.matchingteam.activity.board

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.R
import com.example.matchingteam.activity.HomeActivity
import com.example.matchingteam.api.board.BoardApi
import com.example.matchingteam.connection.RetrofitConnection
import com.example.matchingteam.databinding.ActivityBoardBinding
import com.example.matchingteam.domain.board.Board
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat

class BoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boards()
        binding.buttonWrite.setOnClickListener {
            val intent: Intent = Intent(this@BoardActivity, EnrolBoardActivity::class.java)
            startActivity(intent)
        }
        binding.mainLayout1.setOnClickListener {
            val title = binding.textViewTitle1.text.toString().trim()
            val content = binding.textViewContent1.text.toString().trim()
            val createdDate = binding.hiddenCreatedDate1.text
            val viewCnt = strToIntViewCnt(binding.textViewViewCnt1.text.toString().trim())
            val sp: SharedPreferences =
                getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
            val loginEmail: String = sp.getString("loginEmail", null).toString().trim()
            findBoard(title, content, stringToTimestamp(createdDate.toString()), viewCnt)
        }

        binding.mainLayout2.setOnClickListener {
            val title = binding.textViewTitle2.text.toString().trim()
            val content = binding.textViewContent2.text.toString().trim()
            val createdDate = binding.hiddenCreatedDate2.text
            val viewCnt = strToIntViewCnt(binding.textViewViewCnt2.text.toString().trim())
            val sp: SharedPreferences =
                getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
            val loginEmail: String = sp.getString("loginEmail", null).toString().trim()
            findBoard(title, content, stringToTimestamp(createdDate.toString()), viewCnt)
        }

        binding.mainLayout3.setOnClickListener {
            val title = binding.textViewTitle3.text.toString().trim()
            val content = binding.textViewContent3.text.toString().trim()
            val createdDate = binding.hiddenCreatedDate3.text
            val viewCnt = strToIntViewCnt(binding.textViewViewCnt3.text.toString().trim())
            val sp: SharedPreferences =
                getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
            val loginEmail: String = sp.getString("loginEmail", null).toString().trim()
            findBoard(title, content, stringToTimestamp(createdDate.toString()), viewCnt)
        }

        binding.buttonBoardList.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 모든 프로젝트 모집글 가져오기
     */
    private fun boards() {
        val retrofit = RetrofitConnection.getInstance()
        val api: BoardApi = retrofit.create(BoardApi::class.java)
        val call: Call<List<Board>?> = api.boards()
        call.enqueue(object : Callback<List<Board>?> {
            override fun onResponse(call: Call<List<Board>?>, response: Response<List<Board>?>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val boards: List<Board>? = response.body()
                        if (boards != null) {
                            for (index in boards.indices) {
                                when (index) {
                                    0 -> dynamicLayout1(
                                        boards.get(index).title,
                                        boards.get(index).content,
                                        boards.get(index).createdDate,
                                        "조회수 : " + boards.get(index).viewCnt.toString()
                                    )

                                    1 -> dynamicLayout2(
                                        boards.get(index).title,
                                        boards.get(index).content,
                                        boards.get(index).createdDate,
                                        "조회수 : " + boards.get(index).viewCnt.toString()
                                    )

                                    else -> dynamicLayout3(
                                        boards.get(index).title,
                                        boards.get(index).content,
                                        boards.get(index).createdDate,
                                        "조회수 : " + boards.get(index).viewCnt.toString()
                                    )
                                }
                            }

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Board>?>, t: Throwable) {}
        })
    }

    /**
     * 특정 게시물 가져오기
     */
    private fun findBoard(title: String, content: String, createdDate: Timestamp, viewCnt: Int) {
        val retrofit = RetrofitConnection.getInstance()
        val api: BoardApi = retrofit.create(BoardApi::class.java)
        val call: Call<Board> = api.findBoard(title, content, createdDate, viewCnt)
        call.enqueue(object : Callback<Board> {
            override fun onResponse(call: Call<Board>, response: Response<Board>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val sp: SharedPreferences =
                            getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
                        val loginEmail: String? = sp.getString("loginEmail", null)
                        val intent = Intent(this@BoardActivity, ReadBoardActivity::class.java)
                        intent.putExtra("title", title)
                        intent.putExtra("content", content)
                        intent.putExtra("loginEmail", loginEmail)
                        startActivity(intent)
                    }
                }
            }

            override fun onFailure(call: Call<Board>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    /**
     * Timestamp -> String
     */
    private fun timestampToStr(time: Timestamp): String {
        return SimpleDateFormat("yyyy.MM.dd").format(time)
    }

    private fun stringToTimestamp(time: String): Timestamp {
        return Timestamp.valueOf(time)
    }

    /**
     * 조회수 문자열 제거
     */
    private fun strToIntViewCnt(viewCnt: String): Int {
        return viewCnt.replace("[^0-9]".toRegex(), "").toInt()
    }

    private fun dynamicLayout1(
        title: String,
        content: String,
        createdDate: Timestamp,
        viewCnt: String
    ) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle1.text = title
        binding.textViewContent1.text = content
        binding.textViewCreatedDate1.text = "작성일자 : " + timestampToStr(createdDate)
        binding.textViewViewCnt1.text = viewCnt
        binding.mainLayout1.visibility = View.VISIBLE
        binding.hiddenCreatedDate1.text = createdDate.toString()
    }

    private fun dynamicLayout2(
        title: String,
        content: String,
        createdDate: Timestamp,
        viewCnt: String
    ) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle2.text = title
        binding.textViewContent2.text = content
        binding.textViewCreatedDate2.text = "작성일자 : " + timestampToStr(createdDate)
        binding.textViewViewCnt2.text = viewCnt
        binding.mainLayout2.visibility = View.VISIBLE
        binding.hiddenCreatedDate2.text = createdDate.toString()
    }

    private fun dynamicLayout3(
        title: String,
        content: String,
        createdDate: Timestamp,
        viewCnt: String
    ) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle3.text = title
        binding.textViewContent3.text = content
        binding.textViewCreatedDate3.text = "작성일자 : " + timestampToStr(createdDate)
        binding.textViewViewCnt3.text = viewCnt
        binding.mainLayout3.visibility = View.VISIBLE
        binding.hiddenCreatedDate3.text = createdDate.toString()
    }
}
