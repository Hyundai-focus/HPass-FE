package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityPopupBinding

/**
 *
 * @author 김은서
 *
 */
class NfcPopUpActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.popupNfcButton.setOnClickListener {
            finish();
        }
    }
}
