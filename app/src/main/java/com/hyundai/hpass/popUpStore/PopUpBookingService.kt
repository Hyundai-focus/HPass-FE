package com.hyundai.hpass.popUpStore

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * @author 황수연
 *
 */
interface PopUpBookingService {
    @GET("popup/booking/list")
    suspend fun getBookingsWithinPopupPeriod(
        @Header("Authorization") authorization: String,
        @Query("popupNo") popupNo: Int,
        @Query("popupStartDt") popupStartDt: String,
        @Query("popupEndDt") popupEndDt: String
    ): Response<List<PopUpBookingDTO>>

    @POST("popup/booking")
    suspend fun insert(@Header("Authorization") authorization: String, @Body item: bookingItem): Response<String>
}