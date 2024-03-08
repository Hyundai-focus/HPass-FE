package com.hyundai.hpass.myPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyPageDialogMoreSubsBinding
import com.hyundai.hpass.databinding.MyPageDialogStopSubsBinding
import com.hyundai.hpass.databinding.NewProductDialogApplyNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse

// 작성자: 김은서, 최현서
// 기능: 구독 연장 다이얼로그
class MyPageSubsMoreDialog :DialogFragment() {
    private lateinit var binding: MyPageDialogMoreSubsBinding
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
        binding = MyPageDialogMoreSubsBinding.inflate(inflater, container, false)

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