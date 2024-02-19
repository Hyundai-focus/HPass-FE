package com.hyundai.hpass.myPage

import com.hyundai.hpass.myPage.model.MypageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 *
 * @author 김은서
 *
 */
interface MyPageService {
    @GET("/mypage")
    suspend fun getUserInfo(
        @Header("Authorization") Authorization: String
    ): Response<MypageResponse>
}