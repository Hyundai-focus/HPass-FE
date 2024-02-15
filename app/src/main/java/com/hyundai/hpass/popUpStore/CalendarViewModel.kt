package com.hyundai.hpass.popUpStore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _reservations = MutableLiveData<Map<String, List<Boolean>>>()
    val reservations: LiveData<Map<String, List<Boolean>>> = _reservations

    private val _selectedTime = MutableLiveData<String>()
    val selectedTime: LiveData<String> get() = _selectedTime

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/popup/")
        .addConverterFactory(GsonConverterFactory.create()) // JSON 컨버터 사용
        .build()

    private val bookingService = retrofit.create(PopUpBookingService::class.java)

    init {
        viewModelScope.launch {
            loadReservations()
        }
    }

    private suspend fun loadReservations() {
        try {
            val response = bookingService.getBookingsWithinPopupPeriod(popupNo, popupStartDt, popupEndDt)
            if (response.isSuccessful) {
                val bookings = response.body()

                val reservationMap = mutableMapOf<String, List<Boolean>>()

                bookings?.forEach { booking: PopUpBookingDTO ->
                    val date = booking.bookingDt.substring(0, 10) // YYYY-MM-DD 형식으로 잘라서 날짜만 가져옴
                    val time = booking.bookingTime.trim() // 앞뒤 공백 제거

                    // 예약된 시간을 true로 표시
                    val availabilityList = reservationMap[date]?.toMutableList() ?: MutableList(5) { false }
                    availabilityList[timeToIndex(time)] = true
                    reservationMap[date] = availabilityList
                }

                _reservations.postValue(reservationMap)
            } else {
                _errorMessage.postValue("서버로부터 예상치 못한 응답을 받았습니다: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            _errorMessage.postValue("네트워크 오류: ${e.message}")
        }
    }

    private fun timeToIndex(time: String): Int {
        return when (time) {
            "11 : 00" -> 0
            "13 : 00" -> 1
            "15 : 00" -> 2
            "17 : 00" -> 3
            "19 : 00" -> 4
            else -> throw IllegalArgumentException("잘못된 시간: $time")
        }
    }

    fun updateSelectedTime(time: String) {
        _selectedTime.value = time
    }
}