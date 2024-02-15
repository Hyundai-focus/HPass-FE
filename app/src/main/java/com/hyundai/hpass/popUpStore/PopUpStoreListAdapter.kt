package com.hyundai.hpass.popUpStore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.PopUpStoreListItemBinding
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse

/**
 *
 * @author 김기훈
 *
 */
class PopUpStoreListAdapter(private val storeList: List<PopUpStoreResponse>): RecyclerView.Adapter<PopUpStoreListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: PopUpStoreListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.popUpStoreImage
        val storeName = binding.popUpStoreName
        val period = binding.period
        val storeLocation = binding.location

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            PopUpStoreListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeData = storeList[position]

        Glide
            .with(holder.root.context)
            .load(storeData.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)

        holder.storeName.text = storeData.name
        holder.period.text = storeData.startDate + " ~ " + storeData.endDate
        holder.storeLocation.text = storeData.location
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}