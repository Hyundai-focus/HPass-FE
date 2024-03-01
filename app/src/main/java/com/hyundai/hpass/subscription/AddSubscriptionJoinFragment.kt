package com.hyundai.hpass.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.AddSubscriptionFragmentInfoBinding
import com.hyundai.hpass.databinding.AddSubscriptionFragmentJoinBinding

class AddSubscriptionJoinFragment : Fragment() {
    lateinit var binding: AddSubscriptionFragmentJoinBinding
    private lateinit var checkboxes: List<CheckBox>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddSubscriptionFragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkboxes = listOf(
            binding.checkbox1,
            binding.checkbox2,
            binding.checkbox3,
            binding.checkbox4
        )

        configureEvent()
        bind()
    }

    override fun onResume() {
        super.onResume()
        binding.subsJoinBtn.isEnabled = isJoinEnabled()
    }
    private fun configureEvent() {
        binding.checkboxAll.setOnClickListener {
            checkboxes.forEach { checkbox ->
                checkbox.isChecked = binding.checkboxAll.isChecked
                binding.subsJoinBtn.isEnabled = isJoinEnabled()
            }
        }
        checkboxes.forEach { checkbox ->
            checkbox.setOnClickListener {
                binding.checkboxAll.isChecked = checkboxes.all { it.isChecked }
                binding.subsJoinBtn.isEnabled = isJoinEnabled()
            }
        }
        binding.subsJoinBtn.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.add_subs_container, AddSubscriptionInfoFragment()).addToBackStack(null).commit()
        }
        binding.backButton.setOnClickListener{
            requireActivity().finish()
        }
    }

    private fun isJoinEnabled(): Boolean {
        return checkboxes[0].isChecked && checkboxes[1].isChecked && checkboxes[3].isChecked
    }
    private fun bind() {
    }
}