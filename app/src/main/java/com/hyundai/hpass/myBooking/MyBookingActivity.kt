package com.hyundai.hpass.myBooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.R
import java.time.format.DateTimeFormatter

/**
 *
 * @author 황수연
 *
 */
class MyBookingActivity : AppCompatActivity() {
    private lateinit var viewModel: MyBookingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_booking_activity_my_booking)

        viewModel = ViewModelProvider(this).get(MyBookingViewModel::class.java)

        // 데이터 관찰
        viewModel.bookingInfoList.observe(this, Observer { bookingList ->
            // 예약 정보가 업데이트될 때마다 실행되는 코드
            updateUI(bookingList)
        })
    }

    override fun onResume() {
        super.onResume()
        // 예약 정보 로드
        viewModel.loadBookings()
    }

    private fun updateUI(bookingList: List<MyBookingDTO>) {
        // 예약된 팝업스토어 정보를 UI에 업데이트
        val dateFormat = DateTimeFormatter.ofPattern("yyyy / MM / dd")
        val timeFormat = DateTimeFormatter.ofPattern("hh : mm")

        val recyclerView: RecyclerView = findViewById(R.id.MyBookingList)
        val adapter = MyBookingAdapter(bookingList, dateFormat, timeFormat)
        recyclerView.adapter = adapter
    }
}