package com.hyundai.hpass.newProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.NewProductDialogApplyNewProductBinding
import com.hyundai.hpass.newProduct.model.NewProductViewModel
import com.hyundai.hpass.newProduct.model.request.ApplyNewProdRequest
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse

/**
 *
 * @author 김은서
 *
 */
class ApplyNewProductDialog : DialogFragment() {
    private lateinit var context: Context
    private lateinit var binding: NewProductDialogApplyNewProductBinding
    lateinit var viewModel: NewProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = NewProductDialogApplyNewProductBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NewProductViewModel::class.java]
        context = requireContext()
        val item = arguments?.getSerializable("item",NewItemListResponse::class.java)
        item?.let {
            setDialogUI(binding, it)
        }
        binding.newProductApplyButton.setOnClickListener {
            ApplyNewProduct(item!!.productNo)
        }
        return binding.root
    }

    private fun setDialogUI(binding: NewProductDialogApplyNewProductBinding, item: NewItemListResponse) {
        Glide.with(this)
            .load(item.productImg)
            .into(binding.newProductChosenImg)
        binding.newProductChosenTitle.text = item.productBrand
        binding.newProductChosenSubtitle.text = item.productName
        val locs = item.receiveLoc.split(" ")
        val receivLoc = locs[0] + " " +  locs[1]
        binding.productLocDialog.text = receivLoc
    }

    private fun ApplyNewProduct(prodNumber: Long) {
        viewModel.applyNewProd(ApplyNewProdRequest(prodNumber))
        viewModel.applyStatus.observe(this) { applyStatus ->
            if(applyStatus.equals("success")){
                val intent = Intent(context, UserNewProductActivity::class.java)
                startActivity(intent)
                dismiss() // DialogFragment 닫기
            }
        }
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
