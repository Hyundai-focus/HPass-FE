package com.hyundai.hpass.myVisitStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyVisitStoreActivityMyVisitStoreBinding
import com.hyundai.hpass.myVisitStore.model.MyVIsitStoreViewModel


class MyVisitStoreActivity : AppCompatActivity() {
    lateinit var binding: MyVisitStoreActivityMyVisitStoreBinding
    private lateinit var viewModel: MyVIsitStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyVisitStoreActivityMyVisitStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MyVIsitStoreViewModel::class.java]

        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getStoreList()
    }
    private fun bind(){
        viewModel.storeList.observe(this) {storeList ->
            val checkListItem = binding.myVisitStoreCheckListItem
            checkListItem.adapter = CheckListAdapter(storeList)

            val storeListItem = binding.myVisitStoreStoreList
            storeListItem.adapter = StoreListAdapter(storeList)
        }
    }
}