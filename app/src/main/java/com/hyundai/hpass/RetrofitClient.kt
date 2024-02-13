package com.hyundai.hpass

import com.hyundai.hpass.socialLogIn.MemberService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private const val BASE_URL = "https://jsonplaceholder.typicode.com" // 파일로 빼기
    private const val BASE_URL = "http://10.0.2.2:8080/"


    val memberService: MemberService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MemberService::class.java)
    }
}