package com.hyundai.hpass.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivitySubsStopFinishBinding

/**
 *
 * @author 김은서
 *
 */
class MyPageSubsStopResultActivty : AppCompatActivity() {
    lateinit var binding : MyPageActivitySubsStopFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivitySubsStopFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subStopFinishButton.setOnClickListener {
            finish()
        }
    }
}