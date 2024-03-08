package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyPageActivityMoreSubscriptionBinding
import com.hyundai.hpass.myPage.model.SubsStopViewModel

// 작성자: 김은서, 최현서
// 기능: 구독 연장 액티비티
class MyPageSubsMoreActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivityMoreSubscriptionBinding
    private lateinit var viewModel: SubsStopViewModel
    private lateinit var lastDate: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityMoreSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SubsStopViewModel::class.java]
        lastDate = intent.getStringExtra("lastDate").toString()
        setBtnEvent()
        bind()
    }

    private fun setBtnEvent() {
        binding.backButton.setOnClickListener {
            finish()
        }
        binding.subsMoreButton.setOnClickListener {
            val dialogFragment = MyPageSubsMoreDialog()
            dialogFragment.setCallback {
                viewModel.moreSubscription()
            }
            dialogFragment.show(supportFragmentManager, "SubsMoreDialogTag")
        }
    }

    private fun bind() {
        viewModel.getMoreSubsSuccess().observe(this) { success ->
            if (success) {
                Log.d("구독 연장", "retrofit 통신: 성공")
                val resultIntent = Intent(this, MyPageSubsStopResultActivity::class.java)
                resultIntent.putExtra("lastDate", lastDate)
                resultIntent.putExtra("status", "more")
                startActivity(resultIntent)
                finish()
            } else Log.d("구독 연장", "retrofit 통신: 실패")
        }
    }
}