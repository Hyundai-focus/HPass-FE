package com.hyundai.hpass.newProduct

import com.hyundai.hpass.newProduct.model.request.ApplyNewProdRequest
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

// 작성자: 김은서
// 기능: 신상품 체험 서비스
interface NewProductService {
    // 작성자: 김은서
    // 기능: 신상품 목록
    @GET("/product/new/list")
    suspend fun getProdList(
        @Header("Authorization") Authorization: String
    ):Response<List<NewItemListResponse>>

    // 작성자: 김은서
    // 기능: 신청한 신상품 상태 및 정보
    @GET("/product/new/apply")
    suspend fun usrProductApplyInfo(
        @Header("Authorization") Authorization: String
    ):Response<UsrProdStatusResponse>

    // 작성자: 김은서
    // 기능: 신상품 체험 신청
    @POST("/product/new/apply")
    suspend fun applyNewProd(
        @Header("Authorization") Authorization: String,
        @Body prodNum : ApplyNewProdRequest
    ):Response<String>

    // 작성자: 김은서
    // 기능: 신상품 신청 취소
    @GET("/product/new/cancel")
    suspend fun cancelNewProd(
        @Header("Authorization") Authorization: String
    ):Response<String>
}