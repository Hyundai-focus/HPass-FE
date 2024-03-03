package com.hyundai.hpass.myCoupon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyundai.hpass.databinding.UsingCouponFragmentBinding
import com.hyundai.hpass.myCoupon.model.response.MyCouponResponse

class UsingCouponFragment(private val coupon: MyCouponResponse, private val viewModel: MyCouponViewModel) : BottomSheetDialogFragment() {

    private lateinit var binding: UsingCouponFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsingCouponFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.useButton.setOnClickListener {
            viewModel.useCoupon(coupon.couponNo)
            dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }
}