package com.hyundai.hpass.socialLogIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.ActivitySocialLoginBinding

class SocialLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySocialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}