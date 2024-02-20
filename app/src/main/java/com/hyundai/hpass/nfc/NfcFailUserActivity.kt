package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityFailUserBinding

/**
 *
 * @author 김은서
 *
 */
class NfcFailUserActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityFailUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityFailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.failUserButton.setOnClickListener {
            finish();
        }
    }
}
