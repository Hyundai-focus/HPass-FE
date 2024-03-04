package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MainActivityBinding
import com.hyundai.hpass.socialLogIn.MyApplication


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_green) //상태바 색깔
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureEvent()
        configureUI()
    }

    private fun configureUI() {
        if (MyApplication.preferences.getString("loginPass") == "true") {
            binding.mainPage.setImageResource(R.drawable.main_page_login)
        } else {
            binding.mainPage.setImageResource(R.drawable.main_page_not_login)
        }

    }

    private fun configureEvent() {
        binding.overlay.setOnClickListener {
            val intent = Intent(this, HamburgerMenuActivity::class.java)
            startActivity(intent)
        }
    }
}