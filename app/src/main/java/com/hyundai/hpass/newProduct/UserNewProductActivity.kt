package com.hyundai.hpass.newProduct

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NewProductActivityUserNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemUserHistory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 *
 * @author 김은서
 *
 */
class UserNewProductActivity : AppCompatActivity() {
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

    lateinit var binding : NewProductActivityUserNewProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NewProductActivityUserNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUserProductStatus(binding)

        binding.newProductCancelButton.setOnClickListener {
            cancelProduct()
            gotoNewProduct()
        }
        binding.newProductButtonOk.setOnClickListener{
            gotoNewProduct()
        }
    }
    fun setUserProductStatus(binding: NewProductActivityUserNewProductBinding){
        val userText = user.userName + "님의"
        binding.newProductUserName.text = userText
        binding.newProductTitle.text = user.title
        binding.newProductSubtitle.text = user.itemName
        binding.newProductUserTime.text = dateFormat(user.time)
        binding.newProductUserPlace.text = user.place
        Glide.with(binding.newProductImg.context)
            .load(user.itemImg)
            .into(binding.newProductImg)
    }

    fun cancelProduct(){
        //취소 로직
    }

    fun gotoNewProduct(){
        val intent = Intent(this, NewProductActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun dateFormat(beforeDate : String): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy.MM.dd   HH : mm", Locale.getDefault())

        val date: Date? = inputFormat.parse(beforeDate)
        return date?.let { outputFormat.format(it) }
    }
}

