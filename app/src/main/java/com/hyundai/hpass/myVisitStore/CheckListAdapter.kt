package com.hyundai.hpass.myVisitStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.databinding.MyVisitStoreItemCheckListBinding
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse

/**
 *
 * @author 김은서
 *
 */
class CheckListAdapter(private val items : List<StoreListResponse>) : RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {
    class CheckListViewHolder(val binding: MyVisitStoreItemCheckListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val binding = MyVisitStoreItemCheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        val item = items[position] // 데이터 리스트에서 해당 위치의 데이터를 가져옵니다.
        holder.binding.apply {
            val floor = item.storeFloor.substring(0,2)
            myVisitStoreCheckListFloor.text = floor
            if(item.visitStatus) myVisitStoreStatus.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}