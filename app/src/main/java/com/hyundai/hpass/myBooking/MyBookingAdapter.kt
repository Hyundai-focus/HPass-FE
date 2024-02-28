package com.hyundai.hpass.myBooking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.R

/**
 *
 * @author 황수연
 *
 */
class MyBookingAdapter(private var bookingList: List<MyBookingDTO>, private val viewModel: MyBookingViewModel) : RecyclerView.Adapter<MyBookingAdapter.MyBookingViewHolder>() {

    inner class MyBookingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val bookingStore: TextView = itemView.findViewById(R.id.bookingStore)
        private val period: TextView = itemView.findViewById(R.id.period)
        private val image: ImageView = itemView.findViewById(R.id.popUpStoreImage)
        private val deleteButton: AppCompatButton = itemView.findViewById(R.id.delete_btn)

        fun bind(booking: MyBookingDTO) {
            bookingStore.text = booking.popupName
            val periodText = "${booking.bookingDt.replace("-", ".")} ${booking.bookingTime}"
            period.text = periodText

            Glide.with(itemView)
                .load(booking.popupImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(image)

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

