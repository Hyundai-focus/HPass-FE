package com.hyundai.hpass.popUpStore

import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

/**
 *
 * @author 김기훈
 *
 */
interface PopUpStoreService {
    @GET("popup/list")
    suspend fun getAllPopUpStore (
        @Header("Authorization") authorization: String
    ): Response<List<PopUpStoreResponse>>
}