package com.hyundai.hpass.popUpStore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyundai.hpass.R

/**
 *
 * @author 황수연
 *
 */
class CalendarBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var timeButtons: LinearLayout
    private lateinit var calendarView: CalendarView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pop_up_store_activity_date, container, false)

        calendarView = view.findViewById(R.id.materialCalendar)
        // timeButtons = view.findViewById(R.id.timeSlotRecyclerView)  id// 시간 선택 버튼을 담을 레이아웃 참조

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            // 날짜 변경 이벤트를 처리합니다.
            timeButtons.visibility = View.VISIBLE  // 날짜 선택 시 시간 선택 버튼 보이게 함
        }

        return view
    }


}
