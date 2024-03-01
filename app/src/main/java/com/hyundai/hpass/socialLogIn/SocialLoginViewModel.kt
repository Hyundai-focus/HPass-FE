package com.hyundai.hpass.socialLogIn

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig.PREF_KEY_SUBS
import com.hyundai.hpass.BuildConfig.PREF_KEY_TOKEN
import com.hyundai.hpass.BuildConfig.PREF_VALUE_TRUE
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.model.response.LoginResponse
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 최현서
 *
 */
class SocialLoginViewModel: ViewModel() {
    private val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()
    fun getLoginSuccess(): LiveData<Boolean> = loginSuccess
    fun getErrorMessage(): LiveData<String> = errorMessage

    val profileCallback = object : NidProfileCallback<NidProfileResponse> {
        override fun onSuccess(response: NidProfileResponse) {
            response.profile?.run {
                email?.let { email ->
                    name?.let { name ->
                        Log.d("SocialLoginActivity userInfo", "email:$email memberName:$name")
                        loginWithNaver(email, name)
                    }
                }
            }
        }
        override fun onFailure(httpStatus: Int, message: String) {
            loginSuccess.postValue(false)
            errorMessage.postValue("Profile fetch failed: $message")
        }
        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }
    private val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            Log.d("SocialLoginActivity getAccessToken:", NaverIdLoginSDK.getAccessToken() ?: "null")
            NaverIdLoginSDK.getAccessToken()?.let {
                NidOAuthLogin().callProfileApi(profileCallback)
            }
        }

        override fun onFailure(httpStatus: Int, message: String) {
            errorMessage.postValue("Login failed: $message")
            loginSuccess.postValue(false)
        }

        override fun onError(errorCode: Int, message: String) {
            onFailure(errorCode, message)
        }
    }
    fun authenticateNaver(context: Context) {
        NaverIdLoginSDK.authenticate(context as Activity, oauthLoginCallback)
    }

    private fun loginWithNaver(email: String, name: String) {
        viewModelScope.launch {
            val loginRes = async(Dispatchers.IO) {
                RetrofitClient.memberService.naverLogin(email, name)
            }.await()

            if (loginRes.isSuccessful){
                val loginResponse = loginRes.body()
                if(loginResponse != null){
                    Log.d("SocialLoginActivity Retrofit 통신:", "성공: ${loginResponse.toString()}")
                    Log.d("SocialLoginActivity 자체 JWT 토큰 발급:", "성공: ${loginResponse.accessToken}")
                    MyApplication.preferences.setString("loginPass", true.toString())
                    MyApplication.preferences.setString(PREF_KEY_TOKEN, loginResponse.accessToken)
                    if (loginResponse.isSubscribed) MyApplication.preferences.setString(PREF_KEY_SUBS, PREF_VALUE_TRUE)
                    loginSuccess.postValue(true)
                }
                else {
                    loginSuccess.postValue(false)
                    errorMessage.postValue("loginResponse: null")
                }
            }
            else {
                loginSuccess.postValue(false)
                errorMessage.postValue("로그인 Retrofit 통신 실패: ${loginRes.code()}")
            }
        }
    }


}