package com.hyundai.hpass.myCoupon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyundai.hpass.databinding.MyCouponActivityListBinding

class MyCouponActivity : AppCompatActivity() {

    private lateinit var binding: MyCouponActivityListBinding
    private lateinit var viewModel: MyCouponViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyCouponActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyCouponViewModel::class.java]

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        viewModel.getCoupon().observe(this) { couponList ->
            val myCouponListAdapter = MyCouponListAdapter(couponList)
            binding.couponList.adapter = myCouponListAdapter
            binding.couponList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }
}