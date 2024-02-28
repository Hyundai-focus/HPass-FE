package com.hyundai.hpass.popUpStore

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.hyundai.hpass.R
import com.hyundai.hpass.subscription.SubscriptionMainActivity

/**
 *
 * @author 황수연
 *
 */
class PopUpBookingConfirmationActivity : AppCompatActivity() {
    private lateinit var storeName: TextView
    private lateinit var bookingDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pop_up_store_booking_confirmed)
        storeName = findViewById(R.id.store_name)
        bookingDate = findViewById(R.id.booking_date)

        // 선택된 날짜 및 시간 가져오기
        val selectedDate = intent.getStringExtra("selectedDate")?.replace("-", ".")
        val selectedTime = intent.getStringExtra("selectedTime")

        storeName.text = intent.getStringExtra("storeName")
        bookingDate.text = "$selectedDate $selectedTime"

        val confirmed = findViewById<AppCompatButton>(R.id.confirmed)

        confirmed.setOnClickListener {
            val intent = Intent(this, SubscriptionMainActivity::class.java)
            startActivity(intent)
        }
    }

}
