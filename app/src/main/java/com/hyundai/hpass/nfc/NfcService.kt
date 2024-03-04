package com.hyundai.hpass.nfc

import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import com.hyundai.hpass.nfc.model.PopUpBookingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 *
 * @author 김기훈
 *
 */
interface NfcService {
    @GET("/store/visit/num")
    suspend fun visitNum(
        @Header("Authorization") Authorization: String
    ): Response<Long>

    @GET("/popup/booking/{popUpNo}")
    suspend fun getPopupBooking(
        @Path("popUpNo") popUpNo: Long,
        @Header("Authorization") Authorization: String
    ): Response<PopUpBookingResponse>

    @GET("/store/visit/{storeNo}")
    suspend fun visitStore(
        @Path("storeNo") storeNo: Long,
        @Header("Authorization") Authorization: String
    ): Response<StoreListResponse>

    @GET("/coupon/issue/{couponNo}")
    suspend fun issueCoupon(
        @Path("couponNo") couponNo: Long,
        @Header("Authorization") Authorization: String
    ): Response<Boolean>

    @GET("/coupon/issue/store/{storeNo}")
    suspend fun issueCouponByStore(
        @Path("storeNo") storeNo: Long,
        @Header("Authorization") Authorization: String
    ): Response<Boolean>
}