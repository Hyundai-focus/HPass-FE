package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyPageActivityMainBinding
import com.hyundai.hpass.myPage.model.MyPageViewModel
import com.hyundai.hpass.subscription.SubscriptionMainActivity

/**
 *
 * @author 김은서
 *
 */
class MyPageMainActivity:AppCompatActivity() {
    lateinit var binding: MyPageActivityMainBinding
    private lateinit var viewModel: MyPageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                binding.mypageUserStatus.text="구독중"
                binding.myPageSubsButton.setOnClickListener {
                    val intent = Intent(this, SubscriptionMainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            else{
                binding.mypageUserStatus.text="구독하기"
                binding.myPageSubsButton.setOnClickListener {
//                    val intent = Intent(this, "구독 시작 액티비티 클래스")
//                    startActivity(intent)
                    finish()
                }
            }
        }
        binding.buttonToHome.setOnClickListener {
            finish()
        }
    }
}