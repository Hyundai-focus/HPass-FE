package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyPageDialogStopSubsBinding
import com.hyundai.hpass.databinding.NewProductDialogApplyNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse

/**
 *
 * @author 김은서
 *
 */
class MyPageSubsDialog :DialogFragment() {
    private lateinit var binding: MyPageDialogStopSubsBinding
    private var callback: (() -> Unit)? = null // 콜백 변수
    fun setCallback(callback: () -> Unit) {
        this.callback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Impossible)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MyPageDialogStopSubsBinding.inflate(inflater, container, false)

        binding.myPageStopSubsCancelButton.setOnClickListener {
            dismiss()
        }
        binding.myPageStopSubsFinalButton.setOnClickListener {
            callback?.invoke()
            dismiss()
        }
        return binding.root
    }
}