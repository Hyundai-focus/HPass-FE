package com.hyundai.hpass.socialLogIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.SocialLoginActivitySocialLoginBinding

class SocialLoginActivity : AppCompatActivity() {

    lateinit var binding: SocialLoginActivitySocialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SocialLoginActivitySocialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}