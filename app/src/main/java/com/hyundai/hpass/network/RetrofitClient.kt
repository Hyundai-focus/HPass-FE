package com.hyundai.hpass.network

import com.google.gson.GsonBuilder
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myBooking.MyBookingService
import com.hyundai.hpass.myCoupon.MyCouponService
import com.hyundai.hpass.myPage.MyPageService
import com.hyundai.hpass.myVisitStore.MyVisitStoreService
import com.hyundai.hpass.newProduct.NewProductService
import com.hyundai.hpass.nfc.NfcService
import com.hyundai.hpass.popUpStore.PopUpBookingService
import com.hyundai.hpass.popUpStore.PopUpStoreService
import com.hyundai.hpass.socialLogIn.MemberService
import com.hyundai.hpass.subscription.SubscriptionService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


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

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val subscriptionService: SubscriptionService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SubscriptionService::class.java)
    }

    val bookingService: PopUpBookingService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PopUpBookingService::class.java)
    }

    val myVisitStoreService: MyVisitStoreService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyVisitStoreService::class.java)
    }

    val newProductService : NewProductService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(NewProductService::class.java)
    }

    val myPageService : MyPageService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyPageService::class.java)
    }

    val nfcService : NfcService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(EmptyToNullConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NfcService::class.java)
    }

    val myBookingService: MyBookingService by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MyBookingService::class.java)
    }

    val popUpStoreService: PopUpStoreService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(PopUpStoreService::class.java)
    }

    val myCouponService: MyCouponService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyCouponService::class.java)
    }
}