package com.hyundai.hpass.myPage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myPage.model.Subscription
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 *
 * @author 최현서
 *
 */
class SubsInfoViewModel : ViewModel() {
    private val subscribeInfo: MutableLiveData<Subscription?> = MutableLiveData()
    fun getSubscribeInfo(): LiveData<Subscription?> = subscribeInfo

    init {
        findSubscribeInfo()
    }

    private fun findSubscribeInfo() {
        val accessToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)

        viewModelScope.launch {
            val subsInfoRes = async(Dispatchers.IO) {
                RetrofitClient.subscriptionService.getSubscribeInfo(accessToken)
            }.await()

            if (subsInfoRes.isSuccessful) {
                val subsInfoResponse = subsInfoRes.body()
                if (subsInfoResponse != null) {
                    Log.d("findSubscribeInfo Retrofit 통신:", "성공: $subsInfoResponse")
                    subscribeInfo.postValue(subsInfoResponse)
                } else Log.d("findSubscribeInfo Retrofit 통신:", "실패: Response null")
            } else Log.d("findSubscribeInfo Retrofit 통신:", "실패: 통신 실패")
        }
    }

    fun getNextPaymentDate(startDate: LocalDate): String {
        val nowDate = LocalDate.now()
        val nextDate = if (nowDate.dayOfMonth < startDate.dayOfMonth) {
            startDate.withMonth(nowDate.month.value)
        } else {
            startDate.withMonth(nowDate.month.value + 1)
        }
        if (nextDate.dayOfMonth < startDate.dayOfMonth) {
            return nextDate.withDayOfMonth(nextDate.lengthOfMonth()).toString()
        } else {
            return nextDate.withDayOfMonth(startDate.dayOfMonth).toString()
        }
    }
}