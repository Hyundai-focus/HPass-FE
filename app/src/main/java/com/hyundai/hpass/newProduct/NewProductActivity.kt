package com.hyundai.hpass.newProduct

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.NewProductActivityNewProductBinding
import com.hyundai.hpass.newProduct.model.NewProductViewModel
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse

// 작성자: 김은서
// 기능: 신상품 체험 메인 액티비티
class NewProductActivity : AppCompatActivity() {
    private lateinit var viewModel: NewProductViewModel
    private lateinit var binding: NewProductActivityNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewProductActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[NewProductViewModel::class.java]

        configureEvent()
        bind()
    }

    override fun onResume() {
        super.onResume()
        configureEvent()
        bind()
    }

    private fun configureEvent() {
        viewModel.getProductList()
        viewModel.getUsrProdInfo()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        viewModel.newProductItems.observe(this) { newProductItems ->
            viewModel.userProdInfo.observe(this) { userProdInfo ->
                if (userProdInfo.status) {
                    setUserStatus(userProdInfo)
                    binding.newProductUserHistory.setOnClickListener {
                        val intent = Intent(this, UserNewProductActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    val userText = userProdInfo.memberName + "님!"
                    binding.newProductUserName.text = userText
                    binding.newProductUserProductName.text = "현대백화점의 신제품을 체험해보세요!"
                    binding.newProductUserImg.visibility = View.INVISIBLE
                }
                val newItemList = binding.newProductList
                newItemList.adapter =
                    NewItemListAdapter(userProdInfo.status, newProductItems, supportFragmentManager)
            }
        }
    }

    private fun setUserStatus(user: UsrProdStatusResponse) {
        val userText = user.memberName + "님의 신청 제품"
        binding.newProductUserName.text = userText
        binding.newProductUserProductName.text = user.prodName
        binding.newProductUserImg.visibility = View.VISIBLE
        Glide.with(binding.newProductUserImg.context)
            .load(user.prodImg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.newProductUserImg)
    }

}