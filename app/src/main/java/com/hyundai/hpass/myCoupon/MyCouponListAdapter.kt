package com.hyundai.hpass.myCoupon

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyCouponListItemBinding
import com.hyundai.hpass.myCoupon.model.response.MyCouponResponse

/**
 *
 * @author 김기훈
 *
 */
class MyCouponListAdapter(private val couponList: List<MyCouponResponse>, private val viewModel: MyCouponViewModel): RecyclerView.Adapter<MyCouponListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MyCouponListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.popUpStoreImage
        val location = binding.location
        val name = binding.couponName
        val startDate = binding.startDate
        val endDate = binding.endDate
        val useButton = binding.useButton

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MyCouponListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val coupon = couponList[position]

        Glide.with(holder.itemView.context)
            .load(coupon.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)

        holder.location.text = coupon.brand
        holder.name.text = coupon.content
        val couponStartDate =  coupon.startDate.split('-')[1] + "." + coupon.startDate.split('-')[2]
        val couponEndDate =  coupon.endDate.split('-')[1] + "." + coupon.endDate.split('-')[2]
        holder.startDate.text = couponStartDate
        holder.endDate.text = couponEndDate
        holder.useButton.text = if (coupon.couponIsUsed) "사용완료" else "사용하기"
        holder.useButton.setTextColor(if (coupon.couponIsUsed) holder.itemView.context.getColor(R.color.gray) else holder.itemView.context.getColor(R.color.dark_green))
        holder.name.paintFlags = if (coupon.couponIsUsed) Paint.STRIKE_THRU_TEXT_FLAG else holder.name.paintFlags

        holder.useButton.setOnClickListener {
            if (!coupon.couponIsUsed) {
                val dialogFragment = UsingCouponFragment(coupon, viewModel)
                dialogFragment.show(
                    (holder.itemView.context as MyCouponActivity).supportFragmentManager,
                    dialogFragment.tag
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return couponList.size
    }
}