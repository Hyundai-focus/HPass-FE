package com.hyundai.hpass.popUpStore.model

/**
 *
 * @author 황수연
 *
 */
data class PopUpBookingDTO(
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
    팝업스토어 시작일
     */
    val popupStartDt : String,

    /**
    팝업스토어 종료일
     */
    val popupEndDt : String
)
