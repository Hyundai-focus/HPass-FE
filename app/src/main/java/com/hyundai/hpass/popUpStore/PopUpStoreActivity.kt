package com.hyundai.hpass.popUpStore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyundai.hpass.databinding.PopUpStoreActivityListBinding
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse

class PopUpStoreActivity : AppCompatActivity(), PopUpStoreListAdapter.OnItemClickListener {

    private lateinit var binding: PopUpStoreActivityListBinding
    private lateinit var viewModel: PopUpStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PopUpStoreActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[PopUpStoreViewModel::class.java]

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        viewModel.getPopUpStore().observe(this) { storeList ->
            val popUpStoreListAdapter = PopUpStoreListAdapter(storeList, this)
            binding.popUpStoreList.adapter = popUpStoreListAdapter
            binding.popUpStoreList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onItemClick(storeData: PopUpStoreResponse) {
        Log.d("PopUpStoreActivity", "onItemClick: $storeData")
        val bottomSheetDialogFragment = CalendarBottomSheetDialogFragment(storeData)
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)
    }
}