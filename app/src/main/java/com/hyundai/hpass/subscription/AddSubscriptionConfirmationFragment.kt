package com.hyundai.hpass.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.AddSubscriptionFragmentConfirmationBinding

class AddSubscriptionConfirmationFragment : Fragment() {
    lateinit var binding: AddSubscriptionFragmentConfirmationBinding
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
        bind()
    }
    private fun configureEvent() {
        binding.subsConfText1.text = arguments?.getString("subs_period")
        binding.subsConfText3.text = "정기결제 (${arguments?.getString("subs_payment")})"
        binding.subsConfText4.text = arguments?.getString("subs_endDate")

        binding.confirmBtn.setOnClickListener{
            activity?.finish()
        }
    }
    private fun bind() {

    }
}