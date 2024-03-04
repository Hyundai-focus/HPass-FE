package com.hyundai.hpass.myBooking

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myBooking.model.MyBookingDTO
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.launch

/**
 *
 * @author 황수연
 *
 */
class MyBookingViewModel (): ViewModel() {
    private val _bookingInfoList = MutableLiveData<List<MyBookingDTO>>()

    fun getBookingInfoList() = _bookingInfoList

    val deleteSuccess = MutableLiveData<Boolean>()

    fun loadBookings() {
        viewModelScope.launch {
            try {
                val jwtToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
                val loadBookingRes = RetrofitClient.myBookingService.getMyBooking(jwtToken)
                Log.d("MyBookingViewModel", "loadBookings response: $loadBookingRes")

                if (loadBookingRes.isSuccessful) {
                    val bookings = loadBookingRes.body() ?: emptyList()
                    _bookingInfoList.postValue(bookings)
                } else {
                    Log.d("MyBookingViewModel", "loadBookings response: ${loadBookingRes.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MyBookingViewModel", "Error loading bookings: ${e.message}", e)
            }
        }
    }

    fun deleteBooking(bookingNo: Int) {
        viewModelScope.launch {
            try {
                val jwtToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
                val deleteRes = RetrofitClient.myBookingService.deleteBooking(jwtToken, bookingNo)
                Log.d("MyBookingViewModel", "deleteBooking response: $deleteRes")

                if (deleteRes.isSuccessful) {
                    Log.d("MyBookingViewModel", "성공 deleteBooking response: ${deleteRes.body()}")

                    // 예약 목록 새로고침
                    _bookingInfoList.postValue(_bookingInfoList.value?.filter { it.bookingNo != bookingNo })

                    // 삭제 성공 여부 저장
                    deleteSuccess.postValue(true)
                } else {
                    Log.d("MyBookingViewModel", "실패 deleteBooking response: ${deleteRes.errorBody()?.string()}")

                    // 삭제 실패 여부 저장
                    deleteSuccess.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("MyBookingViewModel", "Error delete bookings: ${e.message}", e)
            }
        }
    }
}