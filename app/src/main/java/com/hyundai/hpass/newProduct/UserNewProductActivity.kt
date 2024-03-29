package com.hyundai.hpass.newProduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.NewProductActivityUserNewProductBinding
import com.hyundai.hpass.newProduct.model.NewProductViewModel
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse

// 작성자: 김은서
// 기능: 신청한 신상품 상태 및 정보 액티비티
class UserNewProductActivity : AppCompatActivity() {
    private lateinit var viewModel: NewProductViewModel
    private lateinit var binding: NewProductActivityUserNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewProductActivityUserNewProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NewProductViewModel::class.java]
        setContentView(binding.root)

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        viewModel.getUsrProdInfo()

        binding.newProductCancelButton.setOnClickListener {
            viewModel.cancelProd()
            viewModel.cancelStatus.observe(this) { cancelStatus ->
                if (cancelStatus.equals("success")) finish()
            }
        }

        binding.newProductButtonOk.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        viewModel.userProdInfo.observe(this) { userProdInfo ->
            setUserProductStatus(userProdInfo)
        }
    }

    private fun setUserProductStatus(prodInfo: UsrProdStatusResponse) {
        val userText = prodInfo.memberName + "님의"
        binding.newProductUserName.text = userText
        binding.newProductTitle.text = prodInfo.prodBrand
        binding.newProductSubtitle.text = prodInfo.prodName
        val time = dateFormat(prodInfo.receiveDt)
        binding.newProductUserTime.text = time
        binding.newProductUserPlace.text = prodInfo.receiveLoc
        Glide.with(binding.newProductImg.context)
            .load(prodInfo.prodImg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.newProductImg)
    }

    private fun dateFormat(beforeDate: String?): String {
        if (beforeDate.isNullOrEmpty()) return "" // null 또는 빈 문자열이면 빈 문자열 반환
        val parts = beforeDate.split(" ")
        return if (parts.isNotEmpty()) {
            parts[0].replace("-", ".")
        } else ""
    }
}

