package com.hyundai.hpass.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.SubscriptionMainStoreItemBinding
import com.hyundai.hpass.subscription.model.response.TodayStoreResponse

/**
 *
 * @author 김기훈
 *
 */
class TodayStoreListAdapter(private val storeList: List<TodayStoreResponse>): RecyclerView.Adapter<TodayStoreListAdapter.MyViewHolder>() {
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
            .load(storeData.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)

        holder.name.text = storeData.name
        holder.floor.text = storeData.floor
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}
