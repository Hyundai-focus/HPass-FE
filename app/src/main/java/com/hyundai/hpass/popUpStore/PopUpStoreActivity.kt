package com.hyundai.hpass.popUpStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.hyundai.hpass.R

class PopUpStoreActivity : AppCompatActivity() {

    private lateinit var button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pop_up_store_activity_pop_up_store)

        button = findViewById(R.id.button)

        button.setOnClickListener {
            // CalendarBottomSheetDialogFragment를 표시
            val bottomSheetDialogFragment = CalendarBottomSheetDialogFragment()
            bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
        }
    }
}