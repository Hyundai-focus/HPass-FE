package com.hyundai.hpass.subscription

import com.hyundai.hpass.socialLogIn.model.response.LoginResponse
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
}