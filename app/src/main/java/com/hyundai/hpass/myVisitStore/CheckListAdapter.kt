package com.hyundai.hpass.myVisitStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyundai.hpass.databinding.MyVisitStoreItemCheckListBinding
import com.hyundai.hpass.myVisitStore.model.response.CheckListResponse

/**
 *
 * @author 김은서
 *
 */
class CheckListAdapter : RecyclerView.Adapter<CheckListAdapter.CheckListViewHolder>() {
    class CheckListViewHolder(val binding: MyVisitStoreItemCheckListBinding) : RecyclerView.ViewHolder(binding.root)

    //통신을 통해 받아올 데이터 -> 각 층, 현재 체크 되어있는지 안되어있는지
    private val items: List<CheckListResponse> = listOf(
        CheckListResponse(1, true),
        CheckListResponse(2, true),
        CheckListResponse(3, false),
        CheckListResponse(4, true),
        CheckListResponse(5, false)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckListViewHolder {
        val binding = MyVisitStoreItemCheckListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CheckListViewHolder, position: Int) {
        val item = items[position] // 데이터 리스트에서 해당 위치의 데이터를 가져옵니다.
        holder.binding.apply {
            val floor = item.floor.toString() + 'F'
            myVisitStoreCheckListFloor.text = floor
            if(item.status) myVisitStoreStatus.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}