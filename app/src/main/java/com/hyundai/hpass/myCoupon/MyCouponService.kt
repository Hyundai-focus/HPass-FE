package com.hyundai.hpass.myCoupon

import com.hyundai.hpass.myCoupon.model.response.MyCouponResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 *
 * @author 김기훈
 *
 */
interface MyCouponService {
    @GET("/coupon/list")
    suspend fun getMyAllCoupon(
        @Header("Authorization") Authorization: String
    ): Response<List<MyCouponResponse>>

    @GET("/coupon/using/{couponNo}")
    suspend fun useCoupon(
        @Header("Authorization") Authorization: String,
        @Path("couponNo") couponNo: Long
    ): Response<Unit>
}