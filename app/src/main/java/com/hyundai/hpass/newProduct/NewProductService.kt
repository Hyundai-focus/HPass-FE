package com.hyundai.hpass.newProduct

import com.hyundai.hpass.newProduct.model.request.ApplyNewProdRequest
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 *
 * @author 김은서
 *
 */
interface NewProductService {
    @GET("/product/new/list")
    suspend fun getProdList(
        @Header("Authorization") Authorization: String
    ):Response<List<NewItemListResponse>>

    @GET("/product/new/apply")
    suspend fun usrProductApplyInfo(
        @Header("Authorization") Authorization: String
    ):Response<UsrProdStatusResponse>

    @POST("/product/new/apply")
    suspend fun applyNewProd(
        @Header("Authorization") Authorization: String,
        @Body prodNum : ApplyNewProdRequest
    ):Response<String>

    @GET("/product/new/cancel")
    suspend fun cancelNewProd(
        @Header("Authorization") Authorization: String
    ):Response<String>
}