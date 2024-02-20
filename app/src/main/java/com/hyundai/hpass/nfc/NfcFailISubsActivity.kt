package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityFailSubsBinding

/**
 *
 * @author 김은서
 *
 */
class NfcFailISubsActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityFailSubsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityFailSubsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.failSubsButton.setOnClickListener {
            finish();
        }
    }
}