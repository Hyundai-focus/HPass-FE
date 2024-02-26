package com.hyundai.hpass.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.socialLogIn.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 최현서
 *
 */
class MainViewModel : ViewModel() {
    private val loginPass: MutableLiveData<Boolean> = MutableLiveData()
    private val isSubscribed: MutableLiveData<Boolean> = MutableLiveData()
    fun getLoginPass(): LiveData<Boolean> = loginPass
    fun getIsSubscribed(): LiveData<Boolean> = isSubscribed

    init {
        isLogin()
    }

    private fun isLogin() {
        val accessToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        Log.d("isLogin: accessToken 유무", accessToken)
        if( accessToken != "null"){
            verifyToken(accessToken)
        } else loginPass.postValue(false)
    }
    private fun verifyToken(accessToken: String) {
        var verifyResponse: LoginResponse? = null
        viewModelScope.launch {
            val verifyRes = async(Dispatchers.IO) {
                RetrofitClient.memberService.verifyToken(accessToken)
            }.await()
            if(verifyRes.isSuccessful){
                verifyResponse = verifyRes.body()
                verifyResponse?.let {
                    Log.d("verifyToken Retrofit 통신:", "성공: $it")
                    if (it.isMember) {
                        MyApplication.preferences.setString(BuildConfig.PREF_KEY_TOKEN, it.accessToken)
                        Log.d("verifyToken: userName", it.memberName)
                        MyApplication.preferences.setString("memberName", it.memberName)
                        loginPass.postValue(true)
                        isSubscribed.postValue(it.isSubscribed)
                    } else loginPass.postValue(false)
                    if (it.isSubscribed) MyApplication.preferences.setString(
                        BuildConfig.PREF_KEY_SUBS,
                        BuildConfig.PREF_VALUE_TRUE
                    )
                }
                if(verifyResponse == null) {
                    loginPass.postValue(false)
                    Log.d("verifyToken Retrofit 통신:", "실패")
                }
            }
            else {
                loginPass.postValue(false)
                Log.d("verifyToken Retrofit 통신:", "실패")
            }
        }
    }
}