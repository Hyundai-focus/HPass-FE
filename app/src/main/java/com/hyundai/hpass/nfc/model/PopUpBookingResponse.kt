package com.hyundai.hpass.nfc.model

data class PopUpBookingResponse(
    val bookingNo: Long,
    val memberNo: Long,
    val popUpNo: Long,
    val bookingTime: String,
    val bookingDate: String
)
