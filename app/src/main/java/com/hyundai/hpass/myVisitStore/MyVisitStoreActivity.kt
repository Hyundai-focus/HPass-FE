package com.hyundai.hpass.myVisitStore

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyVisitStoreActivityMyVisitStoreBinding
import com.hyundai.hpass.myVisitStore.model.MyVisitStoreViewModel

// 작성자: 김은서
// 기능: 오늘의 상점 메인 액티비티
class MyVisitStoreActivity : AppCompatActivity() {
    private lateinit var binding: MyVisitStoreActivityMyVisitStoreBinding
    private lateinit var viewModel: MyVisitStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyVisitStoreActivityMyVisitStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyVisitStoreViewModel::class.java]

        configureEvent()
        bind()
    }
    override fun onResume() {
        super.onResume()
        configureEvent()
        bind()
    }
    private fun configureEvent(){
        viewModel.getStoreList()
        viewModel.getFloorList()
        binding.backButton.setOnClickListener {
            finish()
        }
    }
    private fun bind(){
        viewModel.storeList.observe(this) {storeList ->
            val storeListItem = binding.myVisitStoreStoreList
            storeListItem.adapter = StoreListAdapter(storeList,supportFragmentManager)
        }
        viewModel.floorList.observe(this){floorList ->
            bindFloorText(floorList)
        }
    }
    private fun bindFloorText(floors : List<Long>){
        val context = binding.root.context
        val num = floors.size
        if(num == 0){
            binding.backBar.visibility = View.INVISIBLE
            binding.noStoreVisit.visibility = View.VISIBLE
        }
        for (i in 1..5) {
            val textId = context.resources.getIdentifier("level${i}_text", "id", context.packageName)
            val textView = binding.root.findViewById<TextView>(textId)
            val jelbboId = context.resources.getIdentifier("progress_jelbbo_img$i", "id", context.packageName)
            val jelbboView = binding.root.findViewById<View>(jelbboId)
            val levelBubbleId = context.resources.getIdentifier("level$i", "id", context.packageName)
            val levelBubbleView = binding.root.findViewById<View>(levelBubbleId)
            val barId = context.resources.getIdentifier("progress_bar$i", "id", context.packageName)
            val barView = binding.root.findViewById<View>(barId)
            jelbboView.visibility = View.INVISIBLE
            if(i <= num){
                levelBubbleView.visibility = View.VISIBLE
                textView.visibility = View.VISIBLE
                textView.text = "${floors.get(i - 1)}F"
            }
            if(i == num){
                jelbboView.visibility = View.VISIBLE
                barView.visibility = View.VISIBLE
            }
        }
    }
}