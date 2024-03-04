package com.hyundai.hpass.myVisitStore

import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

// 작성자: 김은서
interface MyVisitStoreService {
    // 작성자: 김은서
    // 기능: 오늘의 상점 목록
    @GET("/store/visit")
    suspend fun getStoreList(
        @Header("Authorization") Authorization: String
    ): Response<List<StoreListResponse>>

    // 작성자: 김은서
    // 기능: 방문한 오늘의 상점 층수 목록 통신
    @GET("/store/visit/floor")
    suspend fun getFloorList(
        @Header("Authorization") Authorization: String
    ): Response<List<Long>>
}