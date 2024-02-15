package com.hyundai.hpass.popUpStore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyundai.hpass.databinding.PopUpStoreActivityListBinding

class PopUpStoreActivity : AppCompatActivity() {

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
            val popUpStoreListAdapter = PopUpStoreListAdapter(storeList)
            binding.popUpStoreList.adapter = popUpStoreListAdapter
            binding.popUpStoreList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }
}