package com.hyundai.hpass.myCoupon

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.databinding.MyCouponListItemBinding
import com.hyundai.hpass.myCoupon.model.response.CouponResponse

/**
 *
 * @author 김기훈
 *
 */
class MyCouponListAdapter(private val couponList: List<CouponResponse>): RecyclerView.Adapter<MyCouponListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: MyCouponListItemBinding): RecyclerView.ViewHolder(binding.root) {

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            MyCouponListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val coupon = couponList[position]

    }

    override fun getItemCount(): Int {
        return couponList.size
    }
}