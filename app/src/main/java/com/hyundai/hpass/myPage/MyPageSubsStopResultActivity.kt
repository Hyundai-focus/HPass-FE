package com.hyundai.hpass.myPage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivitySubsStopFinishBinding

/**
 *
 * @author 김은서
 *
 */
class MyPageSubsStopResultActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivitySubsStopFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivitySubsStopFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
    }

    private fun configureUI() {
        if (intent.getStringExtra("status") == "more") {
            binding.stopSubsTitle.text = "구독 연장이 완료되었습니다."
            binding.nextTimeSee.visibility = View.GONE
            binding.lastDateText.text = "다음 결제일"
        }

        binding.lastDate.text = intent.getStringExtra("lastDate")
    }

    private fun configureEvent() {
        binding.subStopFinishButton.setOnClickListener {
            finish()
        }
    }
}