package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MainActivityBinding
import com.hyundai.hpass.socialLogIn.MyApplication


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_green) //상태바 색깔
        binding = MainActivityBinding.inflate(layoutInflater)
        bind()
        setContentView(binding.root)
        configureEvent()
    }

    private fun configureEvent() {
        binding.overlay.setOnClickListener {
            val intent = Intent(this, HamburgerMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bind() {
        if (MyApplication.preferences.getString("loginPass") == true.toString()) {
            binding.mainPage.setImageResource(R.drawable.main_page_login)
        } else {
            binding.mainPage.setImageResource(R.drawable.main_page_not_login)
        }
    }
}