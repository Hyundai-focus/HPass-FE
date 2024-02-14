package com.hyundai.hpass.socialLogIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.SocialLoginActivitySocialLoginBinding
import com.hyundai.hpass.onBoarding.OnBoardingActivity
import com.hyundai.hpass.subscription.SubscriptionMainActivity

class SocialLoginActivity : AppCompatActivity() {

    private lateinit var viewModel: SocialLoginViewModel
    private lateinit var binding: SocialLoginActivitySocialLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SocialLoginActivitySocialLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SocialLoginViewModel::class.java]

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.naverBtn.setOnClickListener {
            viewModel.authenticateNaver(this@SocialLoginActivity)
        }
    }
    private fun bind() {
        viewModel.getLoginSuccess().observe(this) { success ->
            if (success) {
                val intent = Intent(this, SubscriptionMainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                finish()
            }
        }
        viewModel.errorMessage.observe(this) { message ->
            Log.d("SocialLoginActivity", "Error: $message")
        }
    }
}