package com.hyundai.hpass.socialLogIn

import android.app.Application
import com.hyundai.hpass.BuildConfig
import com.navercorp.nid.NaverIdLoginSDK

/**
 *
 * @author 최현서
 *
 */
class MyApplication: Application() {
    companion object {
        lateinit var instance: MyApplication
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        NaverIdLoginSDK.initialize(this, BuildConfig.OAUTH_CLIENT_ID, BuildConfig.OAUTH_CLIENT_SECRET, BuildConfig.OAUTH_CLIENT_NAME)
    }
}