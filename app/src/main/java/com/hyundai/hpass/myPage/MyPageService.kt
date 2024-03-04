package com.hyundai.hpass.myPage

import com.hyundai.hpass.myPage.model.MypageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header


interface MyPageService {
    // 작성자: 김은서
    // 기능: 마이페이지 정보 가져오기
    @GET("/mypage")
    suspend fun getUserInfo(
        @Header("Authorization") Authorization: String
    ): Response<MypageResponse>
}