package com.hyundai.hpass.newProduct

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NewProductActivityUserNewProductBinding
import com.hyundai.hpass.newProduct.model.NewProductViewModel
import com.hyundai.hpass.newProduct.model.request.ApplyNewProdRequest
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * @author 김은서
 *
 */
class UserNewProductActivity : AppCompatActivity() {
    lateinit var viewModel: NewProductViewModel
    lateinit var binding : NewProductActivityUserNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewProductActivityUserNewProductBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NewProductViewModel::class.java]
        setContentView(binding.root)

        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getUsrProdInfo()
    }
    private fun bind(){
        binding.newProductCancelButton.setOnClickListener {
            viewModel.cancelProd()
            viewModel.cancelStatus.observe(this) { cancelStatus ->
                if(cancelStatus.equals("success")) finish()
            }
        }
        binding.newProductButtonOk.setOnClickListener{
            finish()
        }
        viewModel.userProdInfo.observe(this){userProdInfo->
            setUserProductStatus(userProdInfo)
        }
    }
    fun setUserProductStatus(prodInfo: UsrProdStatusResponse){
        val userText = prodInfo.memberName + "님의"
        binding.newProductUserName.text = userText
        binding.newProductTitle.text = prodInfo.prodBrand
        binding.newProductSubtitle.text = prodInfo.prodName
        val time = dateFormat(prodInfo.receiveDt)
        binding.newProductUserTime.text = time
        binding.newProductUserPlace.text = prodInfo.receiveLoc
        Glide.with(binding.newProductImg.context)
            .load(prodInfo.prodImg)
            .into(binding.newProductImg)
    }

    fun dateFormat(beforeDate: String?): String {
        if (beforeDate.isNullOrEmpty()) return "" // null 또는 빈 문자열이면 빈 문자열 반환
        val parts = beforeDate.split(" ")
        return if (parts.isNotEmpty()) {
            parts[0].replace("-", ".")
        } else ""
    }

}

