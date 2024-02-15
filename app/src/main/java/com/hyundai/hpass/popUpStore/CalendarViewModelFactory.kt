package com.hyundai.hpass.popUpStore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *
 * @author 황수연
 *
 */
class CalendarViewModelFactory(
    private val popupNo: Int,
    private val popupStartDt: String,
    private val popupEndDt: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(popupNo, popupStartDt, popupEndDt) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
