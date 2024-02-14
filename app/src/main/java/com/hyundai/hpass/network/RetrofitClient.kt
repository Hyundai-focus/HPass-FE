package com.hyundai.hpass.network

import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.BuildConfig.BASE_URL

import com.hyundai.hpass.socialLogIn.MemberService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * @author 김기훈
 *
 */
object RetrofitClient {
    private const val BASE_URL = BuildConfig.BASE_URL
//    private const val BASE_URL = "http://10.0.2.2:8080/"

    val memberService: MemberService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MemberService::class.java)
    }
}