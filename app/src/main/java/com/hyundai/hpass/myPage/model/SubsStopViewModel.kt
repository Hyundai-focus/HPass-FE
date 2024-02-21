package com.hyundai.hpass.myPage.model

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
import okhttp3.internal.wait

/**
 *
 * @author 최현서
 *
 */
class SubsStopViewModel: ViewModel() {
    private val stopSubsSuccess : MutableLiveData<Boolean> = MutableLiveData()
    fun getStopSubsSuccess() : LiveData<Boolean> = stopSubsSuccess

    fun stopSubscription(lastDate: String) {
        val accessToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        viewModelScope.launch{
            val subsStopRes = async(Dispatchers.IO) {
                RetrofitClient.subscriptionService.stopSubscription(accessToken, lastDate)
            }.await()
            if (subsStopRes.isSuccessful) stopSubsSuccess.postValue(true)
            else stopSubsSuccess.postValue(false)
        }
    }

}