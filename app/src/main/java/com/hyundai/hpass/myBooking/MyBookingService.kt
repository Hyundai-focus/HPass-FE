package com.hyundai.hpass.myBooking

import com.hyundai.hpass.popUpStore.PopUpBookingDTO
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 *
 * @author 황수연
 *
 */
interface MyBookingService {
    @GET("popup/booking/mylist")
    suspend fun getMyBooking(@Header("Authorization") authorization: String): Response<List<MyBookingDTO>>

    @DELETE("popup/booking/{bookingNo}")
    suspend fun deleteBooking(@Header("Authorization") authorization: String, @Path("bookingNo") bookingNo: Int): Response<String>
}