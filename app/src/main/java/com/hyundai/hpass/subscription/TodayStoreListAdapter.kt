package com.hyundai.hpass.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.SubscriptionMainStoreItemBinding
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse

/**
 *
 * @author 김기훈
 *
 */
class TodayStoreListAdapter(private val storeList: List<StoreListResponse>): RecyclerView.Adapter<TodayStoreListAdapter.MyViewHolder>() {
    inner class MyViewHolder(binding: SubscriptionMainStoreItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.storeImage
        val name = binding.storeName
        val floor = binding.storeFloor
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SubscriptionMainStoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeData = storeList[position]

        Glide
            .with(holder.root.context)
            .load(storeData.storeImg)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)

        holder.name.text = storeData.storeBrand
        holder.floor.text = storeData.storeFloor
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}
