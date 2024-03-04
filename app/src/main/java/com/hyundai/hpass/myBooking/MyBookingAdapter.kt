package com.hyundai.hpass.myBooking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.R
import com.hyundai.hpass.myBooking.model.MyBookingDTO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 *
 * @author 황수연
 *
 */
class MyBookingAdapter(private var bookingList: List<MyBookingDTO>, private val viewModel: MyBookingViewModel) : RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH : mm", Locale.getDefault())
    private val currentDate = Calendar.getInstance()

    inner class MyBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookingStore: TextView = itemView.findViewById(R.id.bookingStore)
        private val period: TextView = itemView.findViewById(R.id.period)
        private val image: ImageView = itemView.findViewById(R.id.popUpStoreImage)
        private val deleteButton: AppCompatButton = itemView.findViewById(R.id.delete_btn)
        private val my_booking_status: ConstraintLayout = itemView.findViewById(R.id.my_booking_status)
        private val status: ConstraintLayout = itemView.findViewById(R.id.status)

        fun bind(booking: MyBookingDTO) {
            bookingStore.text = booking.popupName
            val periodText = "${booking.bookingDt.replace("-", ".")} ${booking.bookingTime}"
            period.text = periodText

            Glide.with(itemView)
                .load(booking.popupImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image)

            // 예약의 날짜와 시간을 파싱하여 Calendar 객체로 변환
            val bookingDate = Calendar.getInstance()
            bookingDate.time = dateFormat.parse("${booking.bookingDt.trim()} ${booking.bookingTime.trim()}")

            // 예약이 지난 경우 삭제 버튼을 비활성화 처리
            if (bookingDate.before(currentDate)) {
                my_booking_status.isEnabled = false
                deleteButton.isEnabled = false
                status.visibility = View.VISIBLE
                my_booking_status.setBackgroundColor(Color.parseColor("#8A000000"))
                deleteButton.setBackgroundColor(Color.parseColor("#8A000000"))
            }

            // 삭제 버튼 클릭 이벤트 처리
            deleteButton.setOnClickListener {
                val context = itemView.context
                val dialogFragment = MyBookingDeleteBottomSheetDialogFragment(booking, viewModel)
                dialogFragment.show((context as MyBookingActivity).supportFragmentManager, dialogFragment.tag)
            }
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

