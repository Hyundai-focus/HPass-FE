package com.hyundai.hpass.myBooking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyBookingDeleteBinding

/**
 *
 * @author 황수연
 *
 */
class MyBookingDeleteBottomSheetDialogFragment(private val booking: MyBookingDTO): BottomSheetDialogFragment() {
    private lateinit var binding: MyBookingDeleteBinding
    private lateinit var storeName: TextView
    private lateinit var bookingDate: TextView
    private lateinit var viewModel: MyBookingViewModel
    private lateinit var no: AppCompatButton
    private lateinit var yes: AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyBookingDeleteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MyBookingViewModel::class.java)

        storeName = view.findViewById(R.id.store_name)
        bookingDate = view.findViewById(R.id.booking_date)
        no = view.findViewById(R.id.no_btn)
        yes = view.findViewById(R.id.yes_btn)

        storeName.text = booking.popupName
        bookingDate.text = "${booking.bookingDt} ${booking.bookingTime}"

        no.setOnClickListener {
            activity?.finish()
            dismiss()
        }

        yes.setOnClickListener {
            val bookingNo = booking.bookingNo
            viewModel.deleteBooking(bookingNo)
        }

        viewModel.deleteSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(requireContext(), MyBookingActivity::class.java)
                startActivity(intent)
            }
        }
    }
}