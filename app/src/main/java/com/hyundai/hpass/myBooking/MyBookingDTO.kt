package com.hyundai.hpass.myBooking

/**
 *
 * @author 황수연
 *
 */
data class MyBookingDTO(
    /**
    예약 아이디
     */
    val bookingNo : Int,

    /**
    멤버 아이디
     */
    val memberNo : Int,

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
    val bookingDt : String,

    /**
    팝업스토어 이름
     */
    val popupName: String,

    /**
    팝업스토어 이름
     */
    val popupImg: String,

)
