package com.hyundai.hpass.myBooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.R
import com.hyundai.hpass.subscription.SubscriptionMainActivity

/**
 *
 * @author 황수연
 *
 */
class MyBookingActivity : AppCompatActivity() {
    private lateinit var viewModel: MyBookingViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyBookingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_booking_activity_my_booking)

        // RecyclerView 초기화
        recyclerView = findViewById(R.id.MyBookingList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyBookingAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MyBookingViewModel::class.java)

        // 데이터 관찰
        viewModel.bookingInfoList.observe(this, Observer { bookingList ->
            // 예약 정보가 업데이트될 때마다 실행되는 코드
            adapter.updateData(bookingList)
        })

        // 예약 정보 로드
        viewModel.loadBookings()
        Log.d("MyBookingActivity", "Loading bookings...")

        backEvent()
    }

    private fun backEvent() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            val intent = Intent(this, SubscriptionMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}