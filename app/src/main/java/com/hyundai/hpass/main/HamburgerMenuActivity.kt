package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.HamburgerMenuActivityBinding
import com.hyundai.hpass.myPage.MyPageMainActivity
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.socialLogIn.SocialLoginViewModel
import com.hyundai.hpass.subscription.AddSubscriptionActivity
import com.hyundai.hpass.subscription.SubscriptionMainActivity

class HamburgerMenuActivity : AppCompatActivity() {

    private lateinit var viewModel: SocialLoginViewModel
    private lateinit var binding: HamburgerMenuActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HamburgerMenuActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SocialLoginViewModel::class.java]
        configureUI()
        configureEvent()
        bind()
    }

    private fun configureUI() {
        if (MyApplication.preferences.getString("loginPass") == "true") {
            binding.login.setImageResource(R.drawable.hamburger_login)
        } else {
            binding.login.setImageResource(R.drawable.hamburger_not_login)
        }
    }

    private fun configureEvent() {
        binding.header.setOnClickListener {
            finish()
        }

        binding.login.setOnClickListener {
            if (MyApplication.preferences.getString("loginPass") == "true") {
                val intent = Intent(this, MyPageMainActivity::class.java)
                startActivity(intent)
            } else {
                viewModel.authenticateNaver(this@HamburgerMenuActivity)
            }
        }

        binding.hMenuText.setOnClickListener {
            Log.d("HamburgerMenuActivity: 로그인 여부",MyApplication.preferences.getString("loginPass"))
            Log.d("HamburgerMenuActivity: 구독 여부",MyApplication.preferences.getString(BuildConfig.PREF_KEY_SUBS))
            if (MyApplication.preferences.getString("loginPass") != "true") {
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            } else if (MyApplication.preferences.getString(BuildConfig.PREF_KEY_SUBS) == BuildConfig.PREF_VALUE_TRUE) {
                // 구독자일 경우
                val intent = Intent(this, SubscriptionMainActivity::class.java)
                startActivity(intent)
            } else {
                // 구독 가입 페이지로 이동
                val intent = Intent(this, AddSubscriptionActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun bind() {
        viewModel.getLoginSuccess().observe(this) { success ->
            if (success) {
                goToMain()
            }
        }
        viewModel.errorMessage.observe(this) { message ->
            Log.d("NaverLogin", "Error: $message")
        }
    }
    private fun goToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }
}