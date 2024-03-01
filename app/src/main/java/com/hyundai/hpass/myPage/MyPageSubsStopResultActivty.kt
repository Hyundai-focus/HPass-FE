package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivitySubsStopFinishBinding
import com.hyundai.hpass.main.MainActivity

/**
 *
 * @author 김은서
 *
 */
class MyPageSubsStopResultActivty : AppCompatActivity() {
    private lateinit var binding : MyPageActivitySubsStopFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivitySubsStopFinishBinding.inflate(layoutInflater)
        if (intent.getStringExtra("status") == "more") {
            binding.stopSubsTitle.text = "구독 연장이 완료되었습니다."
            binding.nextTimeSee.visibility = View.GONE
            binding.lastDateText.text = "다음 결제일"
        }
        binding.lastDate.text = intent.getStringExtra("lastDate")
        setContentView(binding.root)

        binding.subStopFinishButton.setOnClickListener {
//            val mainIntent = Intent(this, MainActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            startActivity(mainIntent)
            finish()
        }
    }
}