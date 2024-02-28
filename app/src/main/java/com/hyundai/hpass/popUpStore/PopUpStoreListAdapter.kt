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
class PopUpStoreListAdapter(private val storeList: List<PopUpStoreResponse>, private val listener: OnItemClickListener): RecyclerView.Adapter<PopUpStoreListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: PopUpStoreListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.popUpStoreImage
        val storeName = binding.popUpStoreName
        val period = binding.period
        val storeLocation = binding.location

        val root = binding.root
    }

    interface OnItemClickListener {
        fun onItemClick(storeData: PopUpStoreResponse)
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
        val startDt = storeData.startDate.split("-")[1] + "." +  storeData.startDate.split("-")[2]
        val endDt = storeData.endDate.split("-")[1] + "." +  storeData.endDate.split("-")[2]
        val dt = "$startDt ~ $endDt"
        holder.period.text = dt
        holder.storeLocation.text = storeData.location

        holder.root.setOnClickListener {
            listener.onItemClick(storeData)
        }
    }

    override fun getItemCount(): Int {
        return storeList.size
    }
}