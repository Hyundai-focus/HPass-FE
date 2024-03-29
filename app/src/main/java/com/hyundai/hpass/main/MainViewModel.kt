package com.hyundai.hpass.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 작성자: 최현서
// 기능: 로그인/회원/구독 검증
class MainViewModel : ViewModel() {
    private val loginPass: MutableLiveData<Boolean> = MutableLiveData()
    private val isSubscribed: MutableLiveData<Boolean> = MutableLiveData()
    fun getLoginPass(): LiveData<Boolean> = loginPass
    fun getIsSubscribed(): LiveData<Boolean> = isSubscribed

    init {
        isLogin()
    }

    private fun isLogin() {
        MyApplication.preferences.setString(BuildConfig.PREF_KEY_SUBS, null)
        val accessToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        Log.d("isLogin: accessToken 유무", accessToken)
        if (accessToken != "null") {
            verifyToken(accessToken)
        } else loginPass.postValue(false)
    }

    private fun verifyToken(accessToken: String) {
        viewModelScope.launch {
            val verifyRes = async(Dispatchers.IO) {
                RetrofitClient.memberService.verifyToken(accessToken)
            }.await()

            if (verifyRes.isSuccessful) {
                verifyRes.body()?.let {
                    Log.d("verifyToken Retrofit 통신:", "성공: $it")
                    if (it.isMember) {
                        MyApplication.preferences.setString(BuildConfig.PREF_KEY_TOKEN, it.accessToken)
                        MyApplication.preferences.setString("memberName", it.memberName)
                        MyApplication.preferences.setString("memberNo", it.memberNo.toString())
                        loginPass.postValue(true)
                        isSubscribed.postValue(it.isSubscribed)
                    } else loginPass.postValue(false)
                    if (it.isSubscribed) MyApplication.preferences.setString(
                        BuildConfig.PREF_KEY_SUBS,
                        BuildConfig.PREF_VALUE_TRUE
                    )
                }
            } else {
                loginPass.postValue(false)
                Log.d("verifyToken Retrofit 통신:", "실패")
            }
        }
    }
}