package com.hyundai.hpass.socialLogIn

import android.app.Application
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.BuildConfig.OAUTH_CLIENT_ID
import com.hyundai.hpass.BuildConfig.OAUTH_CLIENT_NAME
import com.hyundai.hpass.BuildConfig.OAUTH_CLIENT_SECRET
import com.navercorp.nid.NaverIdLoginSDK

/**
 *
 * @author 최현서
 *
 */
class MyApplication: Application() {
    companion object {
        lateinit var preferences: PreferenceUtil
    }
    override fun onCreate() {
        preferences = PreferenceUtil(applicationContext)
        super.onCreate()
        NaverIdLoginSDK.initialize(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME)
    }
}