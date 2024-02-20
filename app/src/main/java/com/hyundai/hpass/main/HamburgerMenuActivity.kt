package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.HamburgerMenuActivityBinding
import com.hyundai.hpass.myPage.MyPageMainActivity
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.subscription.SubscriptionMainActivity

class HamburgerMenuActivity : AppCompatActivity() {

    private lateinit var binding: HamburgerMenuActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HamburgerMenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
    }

    private fun configureUI() {
        if (MyApplication.preferences.getString("loginPass") == "true") {
            binding.menuImage.setImageResource(R.drawable.hamburger_manu_login)
        } else {
            binding.menuImage.setImageResource(R.drawable.hamburger_menu_not_login)
        }
    }

    private fun configureEvent() {
        binding.menuImage.setOnClickListener {
            if (MyApplication.preferences.getString("loginPass") == "true") {
                val intent = Intent(this, MyPageMainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, SubscriptionMainActivity::class.java)
                startActivity(intent)
            }
        }

        binding.menuText.setOnClickListener {
            if (MyApplication.preferences.getString("loginPass") == "false") {
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            } else if (false == false) {
                // 구독자일 경우
                val intent = Intent(this, SubscriptionMainActivity::class.java)
                startActivity(intent)
            } else {
                // 구독 가입 페이지로 이동
            }
        }
    }
}