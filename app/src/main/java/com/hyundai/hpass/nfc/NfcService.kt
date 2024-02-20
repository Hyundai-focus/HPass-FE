package com.hyundai.hpass.nfc

import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 *
 * @author 김은서
 *
 */
interface NfcService {
    @GET("/store/visit/{storeNo}")
    suspend fun visitiStore(
        @Path("storeNo") storeNo: Long,
        @Header("Authorization") Authorization: String
    ): Response<StoreListResponse>

    @GET("/store/visit/num")
    suspend fun visitNum(
        @Header("Authorization") Authorization: String
    ): Response<Long>
}