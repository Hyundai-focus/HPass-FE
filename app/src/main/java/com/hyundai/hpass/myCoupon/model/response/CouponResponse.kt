package com.hyundai.hpass.myCoupon.model.response

import com.google.gson.annotations.SerializedName

data class CouponResponse(
    @SerializedName("coupon_no") val id: String,
    @SerializedName("coupon_brand") val brand: String,
    @SerializedName("coupon_content") val content: String,
    @SerializedName("coupon_start_dt") val startDate: String,
    @SerializedName("coupon_end_dt") val endDate: String,
)
