package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivitySuccessVisitFinalBinding

/**
 *
 * @author 김은서
 *
 */
class NfcVisitFianlActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivitySuccessVisitFinalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivitySuccessVisitFinalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.visitFinalButton.setOnClickListener {
            finish();
        }
    }
}