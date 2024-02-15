package com.hyundai.hpass.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.PopUpStoreActivityPopUpStoreBinding
import com.hyundai.hpass.databinding.SubscriptionMainPopupItemBinding
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse

/**
 *
 * @author 김기훈
 *
 */
class PopUpStoreListAdapter(private val popUpStoreList: List<PopUpStoreResponse>): RecyclerView.Adapter<PopUpStoreListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: SubscriptionMainPopupItemBinding):
        RecyclerView.ViewHolder(binding.root) {
            val storeName = binding.popUpStoreName
            val image = binding.popUpStoreImage

            val root = binding.root
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SubscriptionMainPopupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val popUpStoreData = popUpStoreList[position]

        holder.storeName.text = popUpStoreData.name

        Glide
            .with(holder.root.context)
            .load(popUpStoreData.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return popUpStoreList.size
    }
}