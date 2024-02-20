package com.hyundai.hpass.nfc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hyundai.hpass.databinding.NfcActivityWarnNotTodayVisitBinding
/**
 *
 * @author 김은서
 *
 */
class NfcVisitWarnNotTodayActivity : AppCompatActivity() {
    private lateinit var binding: NfcActivityWarnNotTodayVisitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityWarnNotTodayVisitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bind()
    }
    private fun bind(){
        binding.visitWarnButton.setOnClickListener {
            finish();
        }
    }
}