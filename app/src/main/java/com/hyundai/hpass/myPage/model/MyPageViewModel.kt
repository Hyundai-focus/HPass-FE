package com.hyundai.hpass.myPage.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 작성자: 김은서
// 기능: 마이페이지 뷰모델
class MyPageViewModel:ViewModel() {
    val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val userInfo : MutableLiveData<MypageResponse> = MutableLiveData()

    // 작성자: 김은서
    // 기능: 유저 정보 통신
    fun getUserInfo(){
        viewModelScope.launch {
            val userRes = async(Dispatchers.IO){
                RetrofitClient.myPageService.getUserInfo(token)
            }.await()
            if(userRes.isSuccessful) userInfo.postValue(userRes.body()!!)
            else errorMessage.postValue("유저 정보 통신 실패: ${userRes.code()}")
        }
    }

}