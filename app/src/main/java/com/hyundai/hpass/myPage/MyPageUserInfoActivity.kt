package com.hyundai.hpass.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivityUserInfoBinding

/**
 *
 * @author 김은서
 *
 */
class MyPageUserInfoActivity : AppCompatActivity(){
    lateinit var binding: MyPageActivityUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}