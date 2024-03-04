package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyPageActivityMainBinding
import com.hyundai.hpass.myPage.model.MyPageViewModel
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.subscription.AddSubscriptionActivity

//작성자: 김은서
//기능: 메인페이지의 마이페이지
class MyPageMainActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivityMainBinding
    private lateinit var viewModel: MyPageViewModel
    private var isSubscribed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_black) //상태바 색깔
        window.decorView.systemUiVisibility = 0
        viewModel = ViewModelProvider(this)[MyPageViewModel::class.java]
        binding = MyPageActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
    }

    private fun configureUI() {
        binding.myPageUserName.text = MyApplication.preferences.getString("memberName") + "님"

        if (MyApplication.preferences.getString(BuildConfig.PREF_KEY_SUBS) == BuildConfig.PREF_VALUE_TRUE) {
            binding.mypageUserStatusYes.visibility = View.VISIBLE
            isSubscribed = true
        } else {
            binding.mypageUserStatusNo.visibility = View.VISIBLE
        }
    }

    private fun configureEvent() {
        viewModel.getUserInfo()

        binding.myPageSubsButton.setOnClickListener {
            if (isSubscribed) {
                val intent = Intent(this, MyPageActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, AddSubscriptionActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.buttonToHome.setOnClickListener {
            finish()
        }
    }
}