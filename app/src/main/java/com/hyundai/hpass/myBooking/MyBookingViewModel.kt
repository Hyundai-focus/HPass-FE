package com.hyundai.hpass.myBooking

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

/**
 *
 * @author 황수연
 *
 */
class MyBookingViewModel: ViewModel() {
    private val _bookingInfoList = MutableLiveData<List<MyBookingDTO>>()
    val bookingInfoList: LiveData<List<MyBookingDTO>>
        get() = _bookingInfoList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun loadBookings() {
        viewModelScope.launch {
            val loadBookingRes = async(Dispatchers.IO) {
                val jwtToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
                RetrofitClient.myBookingService.getMyBooking(jwtToken)
            }.await()

            if (loadBookingRes.isSuccessful) {
                val bookings = loadBookingRes.body() ?: emptyList()
                _bookingInfoList.postValue(bookings)
            } else {
                _errorMessage.postValue("서버로부터 예상치 못한 응답을 받았습니다: ${loadBookingRes.errorBody()?.string()}")
            }
        }
    }

}