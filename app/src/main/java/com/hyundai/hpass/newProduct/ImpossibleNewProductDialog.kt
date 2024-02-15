package com.hyundai.hpass.newProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.NewProductDialogImpossibleBinding

/**
 *
 * @author 김은서
 *
 */
class ImpossibleNewProductDialog : DialogFragment() {
    private lateinit var binding: NewProductDialogImpossibleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme_Impossible)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewProductDialogImpossibleBinding.inflate(inflater, container, false)

        binding.newProductImpossibleOkButton.setOnClickListener {
            dismiss() // DialogFragment 닫기
        }
        return binding.root
    }
}