package com.hyundai.hpass.myBooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 *
 * @author 황수연
 *
 */
class MyBookingAdapter(private val bookingList: List<MyBookingDTO>, private val dateFormat: DateTimeFormatter, private val timeFormat: DateTimeFormatter) : RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder>() {

    inner class MyBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookingStore: TextView = itemView.findViewById(R.id.bookingStore)
        private val period: TextView = itemView.findViewById(R.id.period)

        fun bind(booking: MyBookingDTO) {
            bookingStore.text = booking.popupName
            val bookingDate = LocalDateTime.parse(booking.bookingDt, dateFormat)
            val bookingTime = LocalDateTime.parse(booking.bookingTime, timeFormat)
            val periodText = "$bookingDate $bookingTime"
            period.text = periodText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBookingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_booking_list_item, parent, false)
        return MyBookingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyBookingViewHolder, position: Int) {
        val booking = bookingList[position]
        holder.bind(booking)
    }

    override fun getItemCount(): Int {
        return bookingList.size
    }
}
