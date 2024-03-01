package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyPageActivityMainBinding
import com.hyundai.hpass.myPage.model.MyPageViewModel
import com.hyundai.hpass.subscription.AddSubscriptionActivity

/**
 *
 * @author 김은서
 *
 */
class MyPageMainActivity:AppCompatActivity() {
    private lateinit var binding: MyPageActivityMainBinding
    private lateinit var viewModel: MyPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_black) //상태바 색깔
        window.decorView.systemUiVisibility = 0
        binding = MyPageActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MyPageViewModel::class.java]

        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getUserInfo()
    }
    private fun bind(){
        viewModel.userInfo.observe(this){userInfo->
            val nameText = userInfo.name + "님"
            binding.myPageUserName.text = nameText
            if(userInfo.status){//구독자 전용
                binding.mypageUserStatusNo.visibility = View.INVISIBLE
                binding.myPageSubsButton.setOnClickListener {
                    val intent = Intent(this, MyPageActivity::class.java)
                    startActivity(intent)
                }
            }
            else{
                binding.mypageUserStatusYes.visibility = View.INVISIBLE
                binding.myPageSubsButton.setOnClickListener {
                    val intent = Intent(this, AddSubscriptionActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        binding.buttonToHome.setOnClickListener {
            finish()
        }
    }
}