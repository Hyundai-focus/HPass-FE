package com.hyundai.hpass.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivityMainBinding
/**
 *
 * @author 김은서
 *
 */
class MyPageMainActivity:AppCompatActivity() {
    lateinit var binding: MyPageActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureEvent()
        bind()
    }
    private fun configureEvent(){

    }
    private fun bind(){

    }

}