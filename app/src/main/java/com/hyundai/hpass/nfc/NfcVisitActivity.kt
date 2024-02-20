package com.hyundai.hpass.nfc

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NfcActivityStoreVisitBinding
import com.hyundai.hpass.nfc.model.NfcViewModel

/**
 *
 * @author 김은서
 *
 */
class NfcVisitActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityStoreVisitBinding
    private lateinit var viewModel: NfcViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityStoreVisitBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[NfcViewModel::class.java]
        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getVisitInfo(39) //nfc에 저장된 매장 번호
    }
    private fun bind(){
        viewModel.uservisitRes.observe(this){uservisitRes->
            if(uservisitRes.storeFloor.equals("not today")){
                val intent = Intent(this, NfcVisitWarnNotTodayActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(uservisitRes.storeFloor.equals("already")){
                val intent = Intent(this, NfcVisitWarnActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                setContentView(binding.root)
                viewModel.getUsrVisitNum()
                viewModel.userVistiNum.observe(this){userVistiNum->
                    if(userVistiNum == 5L){
                        binding.visitNfcButton.setOnClickListener {
                            val intent = Intent(this, NfcVisitFianlActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }else{
                        binding.visitNfcButton.setOnClickListener {
                            finish()
                        }
                    }
                }
                binding.visitNfcFloor.text = uservisitRes.storeFloor
                binding.visitNfcStoreName.text = uservisitRes.storeBrand
                Glide.with(binding.visitNfcImg.context)
                    .load(uservisitRes.storeImg)
                    .into(binding.visitNfcImg)
            }
        }
    }
}
