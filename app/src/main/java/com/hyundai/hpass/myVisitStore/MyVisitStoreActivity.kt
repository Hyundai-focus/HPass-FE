package com.hyundai.hpass.myVisitStore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyVisitStoreActivityMyVisitStoreBinding

class MyVisitStoreActivity : AppCompatActivity() {
    lateinit var binding: MyVisitStoreActivityMyVisitStoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyVisitStoreActivityMyVisitStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val check_list_item = binding.myVisitStoreCheckListItem
        check_list_item.adapter = CheckListAdapter()

        val store_list_item = binding.myVisitStoreStoreList
        store_list_item.adapter = StoreListAdapter()
    }
}