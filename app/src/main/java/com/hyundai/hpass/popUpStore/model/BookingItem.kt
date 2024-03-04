package com.hyundai.hpass.popUpStore.model

/**
 *
 * @author 황수연
 *
 */
data class BookingItem(
    /**
    팝업스토어 아이디
     */
    val popupNo : Int,

    /**
    팝업스토어 예약시간
     */
    val bookingTime : String,

    /**
    팝업스토어 예약일자
     */
    val bookingDt : String
)
