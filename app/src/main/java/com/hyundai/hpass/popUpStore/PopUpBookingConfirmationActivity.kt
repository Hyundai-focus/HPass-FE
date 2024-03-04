package com.hyundai.hpass.popUpStore

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.PopUpStoreBookingConfirmedBinding
import com.hyundai.hpass.subscription.SubscriptionMainActivity

/**
 *
 * @author 황수연
 *
 */
class PopUpBookingConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: PopUpStoreBookingConfirmedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopUpStoreBookingConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
    }

    private fun configureUI() {
        // 선택된 날짜 및 시간 가져오기
        val selectedDate = intent.getStringExtra("selectedDate")?.replace("-", ".")
        val selectedTime = intent.getStringExtra("selectedTime")

        binding.storeName.text = intent.getStringExtra("storeName")
        binding.bookingDate.text = "$selectedDate $selectedTime"
    }

    private fun configureEvent() {
        binding.confirmed.setOnClickListener {
            val intent = Intent(this, SubscriptionMainActivity::class.java)
            startActivity(intent)
        }
    }
}
