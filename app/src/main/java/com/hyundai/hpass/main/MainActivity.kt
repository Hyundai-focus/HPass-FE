package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MainActivityBinding
import com.hyundai.hpass.socialLogIn.MyApplication


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        bind()
        configureEvent()
    }

    private fun configureEvent() {
        binding.overlay.setOnClickListener {
            val intent = Intent(this, HamburgerMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bind() {
        viewModel.getLoginPass().observe(this) { pass ->
            MyApplication.preferences.setString("loginPass", pass.toString())
            Log.d("MainActivity: 로그인 여부",MyApplication.preferences.getString("loginPass"))

            if (pass) {
                binding.mainPage.setImageResource(R.drawable.main_page_login)
            } else {
                binding.mainPage.setImageResource(R.drawable.main_page_not_login)
            }
        }
    }
}