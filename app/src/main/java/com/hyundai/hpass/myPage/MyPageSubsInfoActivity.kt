package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivitySubscriptionBinding


/**
 *
 * @author 김은서
 *
 */
class MyPageSubsInfoActivity : AppCompatActivity() {
    lateinit var binding : MyPageActivitySubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subscriptionStopButton.setOnClickListener {
            val intent = Intent(this, MyPageSubsStopActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}