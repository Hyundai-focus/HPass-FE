package com.hyundai.hpass.newProduct

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NewProductActivityNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemUserHistory

class NewProductActivity : AppCompatActivity() {
    lateinit var binding: NewProductActivityNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewProductActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //뷰 모델의 유저 정보가 신청한 상태라면 해당 상태에 맞게 상단 변경
        if(user.status) {
            setUserStatus(binding)
            binding.newProductUserHistory.setOnClickListener{
                val intent = Intent(this, UserNewProductActivity::class.java)
                startActivity(intent)
            }
        }

        val newItemList = binding.newProductList
        newItemList.adapter = NewItemListAdapter(supportFragmentManager)

    }
    //더미
    val user : NewItemUserHistory = NewItemUserHistory(
        true,
        "황수연",
        "Re X Re",
        "리바이리 소듐 미네랄 엠플",
        "https://imgprism.ehyundai.com/evntCrdInf/imgPath/202402/06/b0c14928-6ca1-4ea7-9405-58ed5c9c961b.png",
        "2024-02-12T15:30:00",
        "더현대 서울 1F 컨시어지"
    )
    fun setUserStatus(binding: NewProductActivityNewProductBinding){
        val userText = user.userName + "님의 신청 제품"
        binding.newProductUserName.text = userText
        binding.newProductUserProductName.text = user.itemName
        binding.newProductUserImg.visibility= View.VISIBLE
        Glide.with(binding.newProductUserImg.context)
            .load(user.itemImg)
            .into(binding.newProductUserImg)
    }

}