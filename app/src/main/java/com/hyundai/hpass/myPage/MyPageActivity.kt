package com.hyundai.hpass.myPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyPageActivityMyPageBinding
import java.time.LocalDate

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: MyPageActivityMyPageBinding
    private lateinit var viewModel: SubsInfoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyPageActivityMyPageBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SubsInfoViewModel::class.java]
        setContentView(binding.root)
        bind()

        binding.subscriptionStopButton.setOnClickListener {
            val intent = Intent(this, MyPageSubsStopActivity::class.java)
            intent.putExtra("lastDate", binding.myPageSubsDetailNextDate.text)
            startActivity(intent)
            finish()
        }
        binding.backButton.setOnClickListener{
            finish()
        }
    }

    private fun bind() {
        viewModel.getSubscribeInfo().observe(this) {subsInfo ->
            if(subsInfo != null) {
                binding.myPageSubsDetailWay.text = subsInfo.payment
                binding.myPageSubsDetailMoney.text = "월 ₩4500"
                val startDate = subsInfo.subsStartDt
                val startLocalDate  = LocalDate.of(startDate[0], startDate[1], startDate[2])
                binding.myPageSubsDetailStartDate.text = startLocalDate.toString().replace("-", ".")
                binding.myPageSubsDetailNextDate.text = viewModel.getNextPaymentDate(startLocalDate).replace("-", ".")
                if (subsInfo.lastDate == "9999-99-99") {
                    binding.endDateText.text = "결제 예정일"
                    binding.subscriptionStopButton.visibility = View.VISIBLE
                } else {
                    binding.endDateText.text = "만료 예정일"
                }
            }
        }
    }
}