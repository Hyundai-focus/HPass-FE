package com.hyundai.hpass.socialLogIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.SocialLoginActivitySocialLoginBinding
import com.hyundai.hpass.main.MainActivity
import com.hyundai.hpass.subscription.SubscriptionMainActivity

/**
 *
 * @author 최현서
 *
 */
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

//        viewModel.isLogin()
        binding.naverBtn.setOnClickListener {
            viewModel.authenticateNaver(this@SocialLoginActivity)
        }
    }
    private fun bind() {
//        viewModel.getLoginPass().observe(this) {pass ->
//            Log.d("자동 로그인 여부", pass.toString())
//            if (pass) {
//                Log.d("자동 로그인", "PASS")
//                goToMain()
//            }
//            else Log.d("자동 로그인", "FAIL")
//        }

        viewModel.getLoginSuccess().observe(this) { success ->
            if (success) {
                goToMain()
            }
        }
        viewModel.errorMessage.observe(this) { message ->
            Log.d("SocialLoginActivity", "Error: $message")
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