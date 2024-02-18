package com.hyundai.hpass.myVisitStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.MyVisitStoreStoreBinding
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse

/**
 *
 * @author 김은서
 *
 */
class StoreListAdapter(private val items: List<StoreListResponse>) : RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder>() {
    class StoreListViewHolder(val binding: MyVisitStoreStoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        val binding = MyVisitStoreStoreBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StoreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        val item = items[position] // 데이터 리스트에서 해당 위치의 데이터를 가져옵니다.
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.storeImg)
                .into(myVisitStoreImage)
            myVisitStoreFloor.text = item.storeFloor
            myVisitStoreTitle.text = item.storeBrand
            if(item.visitStatus) myVisitStoreStoreStatus.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}