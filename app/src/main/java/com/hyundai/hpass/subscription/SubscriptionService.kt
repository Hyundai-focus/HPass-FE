package com.hyundai.hpass.subscription

import com.hyundai.hpass.myPage.model.Subscription
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 *
 * @author 최현서
 *
 */
interface SubscriptionService {
    @GET("/subscription")
    suspend fun getPaymentToken(
        @Header("Authorization") authorization: String
    ): Response<String>

    @POST("/subscription")
    suspend fun addSubscriber(
        @Header("Authorization") authorization: String,
        @Query("payment") payment: String
    ): Response<ResponseBody>

    @GET("/subscription/info")
    suspend fun getSubscribeInfo(
        @Header("Authorization") authorization: String
    ): Response<Subscription>

    @POST("/subscription/stop")
    suspend fun stopSubscription(
        @Header("Authorization") authorization: String,
        @Query("lastDate") lastDate: String
    ): Response<ResponseBody>

    @POST("/subscription/more")
    suspend fun moreSubscription(
        @Header("Authorization") authorization: String,
    ): Response<ResponseBody>
}