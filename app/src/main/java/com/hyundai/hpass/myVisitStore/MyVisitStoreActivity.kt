package com.hyundai.hpass.myVisitStore

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.databinding.MyVisitStoreActivityMyVisitStoreBinding
import com.hyundai.hpass.myVisitStore.model.MyVIsitStoreViewModel


class MyVisitStoreActivity : AppCompatActivity() {
    private lateinit var binding: MyVisitStoreActivityMyVisitStoreBinding
    private lateinit var viewModel: MyVIsitStoreViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyVisitStoreActivityMyVisitStoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MyVIsitStoreViewModel::class.java]

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
    fun bindFloorText(floors : List<Long>){
        val context = binding.root.context
        val num = floors.size
        for (i in 1..5) {
            val textId = context.resources.getIdentifier("level${i}_text", "id", context.packageName)
            val textView = binding.root.findViewById<TextView>(textId)
            val jelbboId = context.resources.getIdentifier("progress_jelbbo_img$i", "id", context.packageName)
            val jelbboView = binding.root.findViewById<View>(jelbboId)
            jelbboView.visibility = View.INVISIBLE
            if(i <= num)textView.text = "${floors.get(i - 1)}F"
            else{
                val levelBubbleId = context.resources.getIdentifier("level$i", "id", context.packageName)
                val levelBubbleView = binding.root.findViewById<View>(levelBubbleId)
                val barId = context.resources.getIdentifier("progress_bar$i", "id", context.packageName)
                val barView = binding.root.findViewById<View>(barId)

                levelBubbleView.visibility = View.INVISIBLE
                textView.visibility = View.INVISIBLE
                barView.visibility = View.INVISIBLE
            }
            if(i == num) jelbboView.visibility = View.VISIBLE
        }


    }
}