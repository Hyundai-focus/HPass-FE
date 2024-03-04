package com.hyundai.hpass.myBooking

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.R

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

        viewModel = ViewModelProvider(this)[MyBookingViewModel::class.java]

        // 예약 정보 로드
        viewModel.loadBookings()

        configureEvent()
        bind()
    }

    private fun bind() {
        viewModel.getBookingInfoList().observe(this, Observer { bookingList ->
            recyclerView = findViewById(R.id.MyBookingList)
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = MyBookingAdapter(bookingList, viewModel)
            recyclerView.adapter = adapter
        })

        viewModel.deleteSuccess.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(this, "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "예약 취소에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun configureEvent() {
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}