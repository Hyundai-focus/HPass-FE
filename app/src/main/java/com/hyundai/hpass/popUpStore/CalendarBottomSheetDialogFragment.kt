package com.hyundai.hpass.popUpStore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyundai.hpass.R
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.databinding.PopUpStoreActivityBookingBinding
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 *
 * @author 황수연
 *
 */
class CalendarBottomSheetDialogFragment(private val storeData: PopUpStoreResponse) : BottomSheetDialogFragment() {

    private lateinit var binding: PopUpStoreActivityBookingBinding
    private lateinit var viewModel: CalendarViewModel

    private val popupNo: Int by lazy { arguments?.getInt("popupNo") ?: 1 }
    private val popupStartDt: String by lazy { arguments?.getString("popupStartDt") ?: storeData.startDate }
    private val popupEndDt: String by lazy { arguments?.getString("popupEndDt") ?: storeData.endDate }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PopUpStoreActivityBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModelFactory = CalendarViewModelFactory(popupNo, popupStartDt, popupEndDt)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CalendarViewModel::class.java)

//        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { message ->
//            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//        })

        // BottomSheetDialog 전체화면으로 띄우기
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        val timeButtons = arrayOf(
            binding.time1,
            binding.time2,
            binding.time3,
            binding.time4,
            binding.time5
        )

        // CalendarView의 최소 날짜 설정
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startCalendar = Calendar.getInstance().apply {
            time = dateFormat.parse(popupStartDt) ?: Date()
        }
        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1) // 오늘 날짜에서 하루를 더해 내일 날짜로 생성
        }

        // popupStartDt와 내일 날짜 중 더 늦은 날짜를 최소 날짜로 설정
        val minDate = if (tomorrow.after(startCalendar)) tomorrow else startCalendar

        val endCalendar = Calendar.getInstance().apply {
            time = dateFormat.parse(popupEndDt) ?: Date()
        }

        // CalendarView의 기본 선택 날짜 설정 (내일 날짜)
        binding.materialCalendar.apply {
            setMinimumDate(minDate) // 달력의 최소 날짜를 내일로 설정
            setMaximumDate(endCalendar)
            setDate(minDate) // 달력의 선택된 날짜를 내일로 설정
        }

        // 날짜 선택 리스너
        binding.materialCalendar.setOnDayClickListener(object : OnDayClickListener {
            override fun onDayClick(eventDay: EventDay) {
                val calendar = eventDay.calendar
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val dateStr = dateFormat.format(calendar.time)

                // viewModel의 viewModelScope에서 launch하여 loadReservations 함수를 호출
                viewModel.viewModelScope.launch {
                    try {
                        // 비동기로 예약 데이터를 로드
                        viewModel.loadReservations()

                        // 예약 데이터가 업데이트될 때까지 대기
                        viewModel.reservations.observe(viewLifecycleOwner) { reservations ->
                            val availabilities = reservations[dateStr] ?: listOf()

                            // 모든 버튼을 초기 상태로 리셋
                            timeButtons.forEach { button ->
                                button.visibility = View.VISIBLE
                                button.isEnabled = true
                                button.background = ContextCompat.getDrawable(requireContext(), R.drawable.popup_booking_button_background)
                                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                            }

                            // 각 timeButton에 대해, 잔여 시간 데이터에 따라 가시성 설정
                            timeButtons.forEachIndexed { index, button ->
                                if (index < availabilities.size && availabilities[index]) {
                                    button.visibility = View.VISIBLE
                                    button.isEnabled = false
                                    button.background = ContextCompat.getDrawable(requireContext(), R.drawable.popup_booking_invalidated)
                                } else {
                                    button.visibility = View.VISIBLE
                                    button.isEnabled = true
                                    button.background = ContextCompat.getDrawable(requireContext(), R.drawable.popup_booking_button_background)
                                }
                            }
                        }
                    } catch (e: Exception) {
                        // 예외 처리
                        Log.e("CalendarFragment", "Error loading reservations: ${e.message}")
                        Toast.makeText(requireContext(), "Error loading reservations", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        val timeButtonListeners = listOf(
            "11 : 00",
            "13 : 00",
            "15 : 00",
            "17 : 00",
            "19 : 00"
        )

        // 클릭 리스너 설정
        timeButtons.forEachIndexed { index, button ->
            if (button.isEnabled) { // 버튼이 활성화된 경우에만 클릭 리스너를 설정
                button.setOnClickListener {
                    viewModel.updateSelectedTime(timeButtonListeners[index])
                }
            }
        }

        // ViewModel에서 선택된 시간을 관찰하고 UI를 업데이트
        viewModel.selectedTime.observe(viewLifecycleOwner) { selectedTime ->
            updateTimeButtons(selectedTime, timeButtons)
        }

        val btn_booking: AppCompatButton = view.findViewById(R.id.booking_insert)

        btn_booking.setOnClickListener {
            // 선택한 시간대
            val selectedTime = viewModel.selectedTime.value

            // 선택한 날짜
            val selectedDate = dateFormat.format(binding.materialCalendar.selectedDate.time)

            if (!selectedTime.isNullOrEmpty() && !selectedDate.isNullOrEmpty()) {
                val item = bookingItem(
                    popupNo = 1,
                    bookingTime = selectedTime,
                    bookingDt = selectedDate
                )
                Log.d("insert item", item.toString())
                viewModel.insertBooking(item)

                viewModel.bookingSuccess.observe(viewLifecycleOwner) { success ->
                    if (success) {
                        val intent = Intent(requireContext(), PopUpBookingConfirmationActivity::class.java).apply {
                            putExtra("selectedDate", selectedDate)
                            putExtra("selectedTime", selectedTime)
                            putExtra("storeName", storeData.name)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun updateTimeButtons(selectedTime: String, allButtons: Array<AppCompatButton>) {
        val originalBackground = ContextCompat.getDrawable(requireContext(), R.drawable.popup_booking_button_background)
        val selectedBackground = ContextCompat.getDrawable(requireContext(), R.drawable.popup_booking_selected_button_background)

        for (button in allButtons) {
            if (button.isEnabled) { // 버튼이 활성화된 경우에만 배경색과 텍스트 색상을 변경
                if (button.text.toString() == selectedTime) {
                    // 클릭된 버튼인 경우
                    button.background = selectedBackground
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                } else {
                    // 클릭된 버튼이 아닌 경우
                    button.background = originalBackground
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                }
            }
        }
    }
}

