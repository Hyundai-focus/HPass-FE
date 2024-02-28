package com.hyundai.hpass.myVisitStore

import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 *
 * @author 김은서
 *
 */

interface MyVisitStoreService {
    @GET("/store/visit")
    suspend fun getStoreList(
        @Header("Authorization") Authorization: String
    ): Response<List<StoreListResponse>>

    @GET("/store/visit/floor")
    suspend fun getFloorList(
        @Header("Authorization") Authorization: String
    ): Response<List<Long>>
}