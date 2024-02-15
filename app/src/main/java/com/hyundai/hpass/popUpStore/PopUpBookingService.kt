package com.hyundai.hpass.popUpStore

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PopUpBookingService {
    @GET("booking/list")
    suspend fun getBookingsWithinPopupPeriod(
        @Query("popupNo") popupNo: Int,
        @Query("popupStartDt") popupStartDt: String,
        @Query("popupEndDt") popupEndDt: String
    ): Response<List<PopUpBookingDTO>>
}