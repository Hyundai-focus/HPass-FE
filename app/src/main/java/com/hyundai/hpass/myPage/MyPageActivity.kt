package com.hyundai.hpass.myPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyPageActivityMyPageBinding
import com.hyundai.hpass.myCoupon.MyCouponActivity
import com.hyundai.hpass.myVisitStore.MyVisitStoreActivity
import com.hyundai.hpass.newProduct.NewProductActivity
import com.hyundai.hpass.popUpStore.PopUpStoreActivity
import com.hyundai.hpass.socialLogIn.SocialLoginActivity

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.subscriptionStopButton.setOnClickListener {
            val intent = Intent(this, MyPageSubsStopActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}