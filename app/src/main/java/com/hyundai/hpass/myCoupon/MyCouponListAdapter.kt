package com.hyundai.hpass.myCoupon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.MyCouponListItemBinding
import com.hyundai.hpass.myCoupon.model.response.MyCouponResponse

/**
 *
 * @author 김기훈
 *
 */
class MyCouponListAdapter(private val couponList: List<MyCouponResponse>): RecyclerView.Adapter<MyCouponListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MyCouponListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.popUpStoreImage
        val location = binding.location
        val name = binding.couponName
        val startDate = binding.startDate
        val endDate = binding.endDate

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MyCouponListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val coupon = couponList[position]

        Glide.with(holder.root.context)
            .load(coupon.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)
        holder.location.text = coupon.brand
        holder.name.text = coupon.content
        holder.startDate.text = coupon.startDate
        holder.endDate.text = coupon.endDate

    }

    override fun getItemCount(): Int {
        return couponList.size
    }
}