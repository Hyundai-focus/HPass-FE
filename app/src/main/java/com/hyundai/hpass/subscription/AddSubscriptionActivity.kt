package com.hyundai.hpass.subscription

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.AddSubscriptionActivityBinding

class AddSubscriptionActivity : AppCompatActivity() {
    lateinit var binding: AddSubscriptionActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddSubscriptionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.add_subs_container, AddSubscriptionJoinFragment()).commit()
    }
}