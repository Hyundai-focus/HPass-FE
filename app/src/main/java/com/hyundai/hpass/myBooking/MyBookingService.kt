package com.hyundai.hpass.myBooking

import com.hyundai.hpass.popUpStore.PopUpBookingDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 *
 * @author 황수연
 *
 */
interface MyBookingService {
    @GET("popup/booking/mylist")
    suspend fun getMyBooking(@Header("Authorization") authorization: String): Response<List<MyBookingDTO>>
}