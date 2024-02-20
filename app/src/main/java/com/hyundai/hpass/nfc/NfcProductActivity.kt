package com.hyundai.hpass.nfc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NfcActivityProductBinding
import com.hyundai.hpass.nfc.model.NfcViewModel

/**
 *
 * @author 김은서
 *
 */
class NfcProductActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityProductBinding
    private lateinit var viewModel: NfcViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NfcViewModel::class.java]
        binding = NfcActivityProductBinding.inflate(layoutInflater)

        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getUsrProdInfo()
    }
    private fun bind(){
        binding.productNfcButton.setOnClickListener {
            finish();
        }
        viewModel.userProdInfo.observe(this) { userProdInfo ->
            if (userProdInfo.status) { //신청 정보 있음
                if(userProdInfo.receiveLoc.equals("더현대 서울 2F 티슬로")){ //받아온 매장 이름 넣기
                    setContentView(binding.root)
                    binding.productNfcStoreBrand.text = userProdInfo.prodBrand
                    binding.productNfcName.text = userProdInfo.prodName
                    Glide.with(binding.productNfcImg.context)
                        .load(userProdInfo.prodImg)
                        .into(binding.productNfcImg)
                }
                else gotoFail()//다른곳에서 받으세요
            } else gotoFail()//신청 정보 없음
        }
    }
    private fun gotoFail(){
        val intent = Intent(this, NfcFailInfoActivity::class.java)
        startActivity(intent)
        finish()
    }
}
