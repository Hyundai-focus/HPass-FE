package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyPageActivityStopSubscriptionBinding
import com.hyundai.hpass.myPage.model.SubsStopViewModel

// 작성자: 김은서, 최현서
// 기능: 구독 취소 만류 액티비티
class MyPageSubsStopActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivityStopSubscriptionBinding
    private lateinit var viewModel: SubsStopViewModel
    private lateinit var lastDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityStopSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SubsStopViewModel::class.java]
        lastDate = intent.getStringExtra("lastDate").toString()
        setBtnEvent()
        bind()
    }

    private fun setBtnEvent(){
        binding.subsNoStopButton.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.continueStopSubs.setOnClickListener {
            val dialogFragment = MyPageSubsDialog()
            dialogFragment.setCallback {
                viewModel.stopSubscription(lastDate)
            }
            dialogFragment.show(supportFragmentManager, "SubsStopDialogTag")
        }
    }
    private fun bind() {
        viewModel.getStopSubsSuccess().observe(this) {success ->
            if(success) {
                Log.d("구독 취소", "retrofit 통신: 성공")
                val resultIntent = Intent(this, MyPageSubsStopResultActivity::class.java)
                resultIntent.putExtra("lastDate", lastDate)
                startActivity(resultIntent)
                finish()
            }
            else Log.d("구독 취소", "retrofit 통신: 실패")
        }
    }
}