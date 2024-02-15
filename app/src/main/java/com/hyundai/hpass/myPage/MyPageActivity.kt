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
    lateinit var binding: MyPageActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserName(binding)
        setBtnEvent(binding)
    }

    val username = "홍길동"

    fun setUserName(binding: MyPageActivityMyPageBinding){
        //binding.mypageUserName = user.username
        val userName = username + "님"
        binding.mypageUserName.text = userName
    }

    fun setBtnEvent(binding: MyPageActivityMyPageBinding){
        binding.mypageButtonLogout.setOnClickListener {
            //로그아웃 메서드
            val intent = Intent(this, SocialLoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        binding.myPageHPass.setOnClickListener {
            val intent = Intent(this, MyPageSubsInfoActivity::class.java)
            startActivity(intent)
        }

        binding.myPageUserInfoButton.setOnClickListener {
            val intent = Intent(this, MyPageUserInfoActivity::class.java)
            startActivity(intent)
        }

        binding.myPagePopupButton.setOnClickListener {
            val intent = Intent(this, PopUpStoreActivity::class.java)
            startActivity(intent)
        }

        binding.myPageNewProdButton.setOnClickListener {
            val intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        }

        binding.myPageCouponButton.setOnClickListener {
            val intent = Intent(this, MyCouponActivity::class.java)
            startActivity(intent)
        }

        binding.myPageTodayStoreButton.setOnClickListener {
            val intent = Intent(this, MyVisitStoreActivity::class.java)
            startActivity(intent)
        }
    }
}