package com.hyundai.hpass.myBooking

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

/**
 *
 * @author 황수연
 *
 */
class MyBookingViewModel(private val bookingNo: Int): ViewModel() {
    private val _bookingInfoList = MutableLiveData<List<MyBookingDTO>>()
    val bookingInfoList: LiveData<List<MyBookingDTO>>
        get() = _bookingInfoList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _successMessage = MutableLiveData<String>()
    val successMessage: LiveData<String> = _successMessage

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
                    _errorMessage.postValue("서버로부터 예상치 못한 응답을 받았습니다: ${loadBookingRes.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("MyBookingViewModel", "Error loading bookings: ${e.message}", e)
                _errorMessage.postValue("예약 정보를 불러오는 중 오류가 발생했습니다: ${e.message}")
            }
        }
    }

    fun deleteBooking() {
        viewModelScope.launch {
            try {
                val jwtToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
                val deleteRes = RetrofitClient.myBookingService.deleteBooking(jwtToken, bookingNo)
                Log.d("MyBookingViewModel", "deleteBooking response: $deleteRes")

                if (deleteRes.isSuccessful) {
                    _successMessage.postValue("예약이 성공적으로 취소되었습니다.")

                    // 예약 목록 새로고침
                    loadBookings()

                    // 삭제 성공 여부 저장
                    deleteSuccess.postValue(true)
                } else {
                    _errorMessage.postValue("서버로부터 예상치 못한 응답을 받았습니다: ${deleteRes.errorBody()?.string()}")

                    // 삭제 실패 여부 저장
                    deleteSuccess.postValue(false)
                }
            } catch (e: Exception) {
                Log.e("MyBookingViewModel", "Error delete bookings: ${e.message}", e)
                _errorMessage.postValue("예약을 삭제하는 중 오류가 발생했습니다: ${e.message}")
            }
        }
    }

    constructor(): this(0)
}