package com.hyundai.hpass.myCoupon.model.response

import com.google.gson.annotations.SerializedName

data class MyCouponResponse(
    @SerializedName("couponHistoryNo") val historyNo: Long,
    @SerializedName("couponNo") val couponNo: Long,
    @SerializedName("memberNo") val memberNo: Long,
    @SerializedName("couponIsUsed") val couponIsUsed: Boolean,
    @SerializedName("couponBrand") val brand: String,
    @SerializedName("couponContent") val content: String,
    @SerializedName("couponStartDate") val startDate: String,
    @SerializedName("couponEndDate") val endDate: String,
    @SerializedName("couponImage") val image: String
)
