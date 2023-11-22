package com.example.matchingteam.board

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.matchingteam.R
import com.example.matchingteam.connection.RetrofitConnection
import com.example.matchingteam.databinding.ActivityBoardBinding
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
                                        "조회수 : " + timestampToStr(boards.get(index).createdDate),
                                        boards.get(index).viewCnt,
                                    )

                                    1 -> dynamicLayout2(
                                        boards.get(index).title,
                                        boards.get(index).content,
                                        "조회수 : " + timestampToStr(boards.get(index).createdDate),
                                        boards.get(index).viewCnt,
                                    )

                                    else -> dynamicLayout3(
                                        boards.get(index).title,
                                        boards.get(index).content,
                                        "조회수 : " + timestampToStr(boards.get(index).createdDate),
                                        boards.get(index).viewCnt,
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
     * Timestamp -> String
     */
    private fun timestampToStr(time: Timestamp): String {
        return SimpleDateFormat("yyyy.MM.dd").format(time)
    }

    private fun dynamicLayout1(title: String, content: String, createdDate: String, viewCnt: Int) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle1.text = title
        binding.textViewContent1.text = content
        binding.textViewCreatedDate1.text = createdDate
        binding.textViewViewCnt1.text = viewCnt.toString()
        binding.mainLayout1.visibility = View.VISIBLE
    }

    private fun dynamicLayout2(title: String, content: String, createdDate: String, viewCnt: Int) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle2.text = title
        binding.textViewContent2.text = content
        binding.textViewCreatedDate2.text = createdDate
        binding.textViewViewCnt2.text = viewCnt.toString()
        binding.mainLayout2.visibility = View.VISIBLE
    }

    private fun dynamicLayout3(title: String, content: String, createdDate: String, viewCnt: Int) {
        val binding = ActivityBoardBinding.bind(findViewById(R.id.rootBoardLayout))
        binding.textViewTitle3.text = title
        binding.textViewContent3.text = content
        binding.textViewCreatedDate3.text = createdDate
        binding.textViewViewCnt3.text = viewCnt.toString()
        binding.mainLayout3.visibility = View.VISIBLE
    }
}
