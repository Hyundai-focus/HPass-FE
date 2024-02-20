package com.hyundai.hpass.subscription

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.AddSubscriptionFragmentInfoBinding
import java.time.LocalDate

class AddSubscriptionInfoFragment : Fragment() {
    lateinit var binding: AddSubscriptionFragmentInfoBinding
    private lateinit var checkboxes: List<CheckBox>
    lateinit var viewModel: AddSubscriptionInfoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddSubscriptionFragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkboxes = listOf(binding.checkbox1, binding.checkbox2)
        viewModel = ViewModelProvider(this)[AddSubscriptionInfoViewModel::class.java]
        binding.phoneNumberInput.filters = arrayOf(InputFilter.LengthFilter(11))
        configureEvent()
        bind()
    }

    override fun onResume() {
        super.onResume()
        checkButtonEnabled()
        binding.root.visibility = View.VISIBLE
    }
    override fun onPause() {
        super.onPause()
        binding.root.visibility = View.INVISIBLE
    }
    private fun configureEvent() {
        binding.phoneNumberInput.addTextChangedListener (object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                checkButtonEnabled()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        binding.checkboxAll.setOnClickListener {
            checkboxes.forEach {checkbox ->
                checkbox.isChecked = binding.checkboxAll.isChecked
            }
            checkButtonEnabled()
        }
        checkboxes.forEach {checkbox ->
            checkbox.setOnClickListener {
                binding.checkboxAll.isChecked = checkboxes.all { it.isChecked }
                checkButtonEnabled()
            }
        }
        binding.purchaseBtn.setOnClickListener{
            viewModel.getBootPayToken()
        }
    }
    fun checkButtonEnabled() {
        binding.purchaseBtn.isEnabled = binding.checkboxAll.isChecked && binding.phoneNumberInput.text.length == 11
    }

    private fun bind() {
        binding.subsPeriod.text = viewModel.calculatePaymentDate(LocalDate.now(),0)
        binding.subsEndDate.text = viewModel.calculatePaymentDate(LocalDate.now(),1)
        viewModel.getUserToken().observe(viewLifecycleOwner){ userToken ->
            if(userToken != "" && userToken != null) {
                viewModel.purchaseSubscribe(binding.phoneNumberInput.text.toString(), userToken, requireContext().applicationContext)
            }
        }
        viewModel.getSubsSuccess().observe(viewLifecycleOwner){payment ->
            if (payment != "") {
                val bundle = Bundle().apply {
                    putString("subs_period", binding.subsPeriod.text.toString())
                    putString("subs_endDate", binding.subsEndDate.text.toString())
                    putString("subs_payment", payment)
                }
                parentFragmentManager.popBackStack()
                parentFragmentManager.beginTransaction().replace(R.id.add_subs_container, AddSubscriptionConfirmationFragment().apply {arguments = bundle}).commit()
            }
            else Log.d("bootPay", "구독 결제 통신 실패")
        }
    }
}