package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
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
        binding.lastDate.text = intent.getStringExtra("lastDate")
        setContentView(binding.root)

        binding.subStopFinishButton.setOnClickListener {
            val mainIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(mainIntent)
            finish()
        }
    }
}