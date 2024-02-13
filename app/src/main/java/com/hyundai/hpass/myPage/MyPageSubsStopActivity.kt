package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.MyPageActivityStopSubscriptionBinding
import com.hyundai.hpass.newProduct.ImpossibleNewProductDialog

/**
 *
 * @author 김은서
 *
 */
class MyPageSubsStopActivity : AppCompatActivity() {
    lateinit var binding: MyPageActivityStopSubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityStopSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBtnEvent(binding)
    }

    fun setBtnEvent(binding: MyPageActivityStopSubscriptionBinding){
        binding.subsNoStopButton.setOnClickListener {
            finish()
        }
        binding.continueStopSubs.setOnClickListener {
            val dialogFragment = MyPageSubsDialog()
            dialogFragment.setCallback {
                val intent = Intent(this, MyPageSubsStopResultActivty::class.java)
                startActivity(intent)
                finish()
            }
            dialogFragment.show(supportFragmentManager, "SubsStopDialogTag")
        }
    }
}