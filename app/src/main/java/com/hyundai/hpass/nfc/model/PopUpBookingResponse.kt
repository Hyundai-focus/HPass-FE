package com.hyundai.hpass.nfc.model

import com.google.gson.annotations.SerializedName

data class PopUpBookingResponse(
    @SerializedName("bookingNo") val bookingNo: Long,
    @SerializedName("memberNo") val memberNo: Long,
    @SerializedName("popupNo") val popUpNo: Long,
    @SerializedName("bookingTime") val bookingTime: String,
    @SerializedName("bookingDt") val bookingDate: String,
    @SerializedName("popupName") val popUpName: String,
    @SerializedName("popupStartDt") val popUpStartDate: String,
    @SerializedName("popupEndDt") val popUpEndDate: String,
    @SerializedName("popupLocation") val popUpLocation: String,
    @SerializedName("popupImg") val popUpImage: String
)
