package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityWarnVisitBinding

/**
 *
 * @author 김은서
 *
 */
class NfcVisitWarnActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityWarnVisitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityWarnVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.visitWarnButton.setOnClickListener {
            finish();
        }
    }
}