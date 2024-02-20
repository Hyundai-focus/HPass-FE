package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityFailInfoBinding

/**
 *
 * @author 김은서
 *
 */
class NfcFailInfoActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityFailInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityFailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bind()
    }
    private fun bind(){
        binding.failInfoButton.setOnClickListener {
            finish();
        }
    }
}
