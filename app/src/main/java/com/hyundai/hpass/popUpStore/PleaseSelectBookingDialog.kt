package com.hyundai.hpass.popUpStore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.PopUpStoreBookingDialogImpossibleBinding
import com.hyundai.hpass.databinding.PopUpStoreBookingDialogSelectBinding

/**
 *
 * @author 황수연
 *
 */
class PleaseSelectBookingDialog : DialogFragment() {
    private lateinit var binding: PopUpStoreBookingDialogSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Impossible)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PopUpStoreBookingDialogSelectBinding.inflate(inflater, container, false)

        binding.bookingImpossibleOkButton.setOnClickListener {
            dismiss() // DialogFragment 닫기
        }
        return binding.root
    }
}