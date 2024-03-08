package com.hyundai.hpass.subscription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyundai.hpass.databinding.AddSubscriptionFragmentConfirmationBinding

// 작성자: 최현서
// 기능: 구독 완료
class AddSubscriptionConfirmationFragment : Fragment() {
    private lateinit var binding: AddSubscriptionFragmentConfirmationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddSubscriptionFragmentConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureEvent()
        configureUI()
    }
    private fun configureEvent() {


        binding.confirmBtn.setOnClickListener{
            activity?.finish()
        }
    }
    private fun configureUI() {
        binding.subsConfText1.text = arguments?.getString("subs_period")
        binding.subsConfText3.text = "정기결제 (${arguments?.getString("subs_payment")})"
        binding.subsConfText4.text = arguments?.getString("subs_endDate")
    }
}