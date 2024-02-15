package com.hyundai.hpass.newProduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.NewProductDialogApplyNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse

/**
 *
 * @author 김은서
 *
 */
class ApplyNewProductDialog : DialogFragment() {
    private lateinit var binding: NewProductDialogApplyNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NewProductDialogApplyNewProductBinding.inflate(inflater, container, false)

        val item = arguments?.getSerializable("item",NewItemListResponse::class.java)
        item?.let {
            setDialogUI(binding, it)
        }

        binding.newProductApplyButton.setOnClickListener {
            ApplyNewProduct()
            dismiss() // DialogFragment 닫기
        }
        return binding.root
    }

    private fun setDialogUI(binding: NewProductDialogApplyNewProductBinding, item: NewItemListResponse) {
        Glide.with(this)
            .load(item.img)
            .into(binding.newProductChosenImg)
        binding.newProductChosenTitle.text = item.title
        binding.newProductChosenSubtitle.text = item.subTitle
    }

    private fun ApplyNewProduct() {
        // 해당 제품 신청 메서드
    }

    companion object {
        fun newInstance(item: NewItemListResponse): ApplyNewProductDialog {
            val args = Bundle().apply {
                putSerializable("item", item)
            }
            return ApplyNewProductDialog().apply {
                arguments = args
            }
        }
    }
}
