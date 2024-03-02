package com.hyundai.hpass.popUpStore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig.PREF_KEY_TOKEN
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
class CalendarViewModel(
    private val popupNo: Int,
    private val popupStartDt: String,
    private val popupEndDt: String
) : ViewModel() {
    private val _bookingSuccess = MutableLiveData<Boolean>()
    val bookingSuccess: LiveData<Boolean> = _bookingSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _reservations = MutableLiveData<Map<String, List<Boolean>>>()
    val reservations: LiveData<Map<String, List<Boolean>>> = _reservations

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> get() = _selectedTime

    fun loadReservations() {
        viewModelScope.launch {
            val loadRes = async(Dispatchers.IO) {
                val jwtToken = MyApplication.preferences.getString(PREF_KEY_TOKEN)
                RetrofitClient.bookingService.getBookingsWithinPopupPeriod(
                    jwtToken,
                    popupNo,
                    popupStartDt,
                    popupEndDt
                )
            }.await()

            if (loadRes.isSuccessful) {
                val bookings = loadRes.body()

                val reservationMap = mutableMapOf<String, List<Boolean>>()

                bookings?.forEach { booking: PopUpBookingDTO ->
                    val date = booking.bookingDt.substring(0, 10) // YYYY-MM-DD 형식으로 잘라서 날짜만 가져옴
                    val time = booking.bookingTime.trim() // 앞뒤 공백 제거

                    fun timeToIndex(time: String): Int {
                        return when (time) {
                            "09 : 00" -> 0
                            "11 : 00" -> 1
                            "13 : 00" -> 2
                            "15 : 00" -> 3
                            "17 : 00" -> 4
                            "19 : 00" -> 5
                            else -> throw IllegalArgumentException("잘못된 시간: $time")
                        }
                    }

                    // 예약된 시간을 true로 표시
                    val availabilityList =
                        reservationMap[date]?.toMutableList() ?: MutableList(5) { false }
                    availabilityList[timeToIndex(time)] = true
                    reservationMap[date] = availabilityList
                }

                _reservations.postValue(reservationMap)
            } else {
                _errorMessage.postValue("서버로부터 예상치 못한 응답을 받았습니다: ${loadRes.errorBody()?.string()}")
            }
        }
    }

    fun insertBooking(item: bookingItem) {
        viewModelScope.launch {
            try {
                val insertRes = async(Dispatchers.IO) {
                    val jwtToken = MyApplication.preferences.getString(PREF_KEY_TOKEN)
                    RetrofitClient.bookingService.insert(jwtToken, item)
                }.await()

                if (insertRes.isSuccessful) {
                    Log.d("여기야 여기", insertRes.body().toString())
                    _bookingSuccess.postValue(true)
                } else {
                    _bookingSuccess.postValue(false)
                    _errorMessage.postValue(
                        "서버로부터 예상치 못한 응답을 받았습니다: ${
                            insertRes.errorBody()?.string()
                        }"
                    )
                }
            } catch (e: Exception) {
                _bookingSuccess.postValue(false)
                Log.e("InsertBooking", "Error inserting booking: ${e.message}", e)
                _errorMessage.postValue("예약 정보를 삽입하는 중 오류가 발생했습니다: ${e.message}")
            }
        }
    }

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
    }
}