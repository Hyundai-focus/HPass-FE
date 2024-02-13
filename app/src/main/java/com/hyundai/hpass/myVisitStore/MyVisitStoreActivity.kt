package com.hyundai.hpass.myVisitStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.databinding.MyVisitStoreActivityMyVisitStoreBinding

class MyVisitStoreActivity : AppCompatActivity() {
    lateinit var binding: MyVisitStoreActivityMyVisitStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyVisitStoreActivityMyVisitStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkListItem = binding.myVisitStoreCheckListItem
        checkListItem.adapter = CheckListAdapter()

        val storeListItem = binding.myVisitStoreStoreList
        storeListItem.adapter = StoreListAdapter()
    }
}