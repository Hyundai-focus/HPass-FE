package com.hyundai.hpass.subscription

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.AddSubscriptionActivityBinding

// 작성자: 최현서
// 기능: 구독 추가
class AddSubscriptionActivity : AppCompatActivity() {
    private lateinit var binding: AddSubscriptionActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddSubscriptionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.add_subs_container, AddSubscriptionJoinFragment()).commit()
    }
}