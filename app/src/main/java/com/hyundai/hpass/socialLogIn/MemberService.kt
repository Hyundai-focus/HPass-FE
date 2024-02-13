package com.hyundai.hpass.socialLogIn

import com.hyundai.hpass.socialLogIn.model.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
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
interface MemberService {
    @POST("/user/login")
    suspend fun naverLogin(
        @Query("email") email: String,
        @Query("memberName") memberName: String
    ): Response<LoginResponse>
    @POST("/subscription")
    suspend fun addSubscriber(
        @Header("Authorization") Authorization: String,
        @Query("payment") payment: String
    ): Response<ResponseBody>
}