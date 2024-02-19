package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MainActivityBinding
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.socialLogIn.SocialLoginViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: SocialLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SocialLoginViewModel::class.java]

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.mainPage.setOnClickListener {
            val intent = Intent(this, HamburgerMenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bind() {
        viewModel.getLoginPass().observe(this) { pass ->
            MyApplication.preferences.setString("loginPass", pass.toString())

            if (pass) {
                binding.mainPage.setImageResource(R.drawable.main_page_login)

            } else {
                binding.mainPage.setImageResource(R.drawable.main_page_not_login)
            }
        }
    }
}