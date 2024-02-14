package com.hyundai.hpass.subscription

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.SubscriptionMainNewproductItemBinding
import com.hyundai.hpass.databinding.SubscriptionMainPopupItemBinding
import com.hyundai.hpass.subscription.model.response.NewProductResponse

/**
 *
 * @author 김기훈
 *
 */
class NewProductListAdapter(private val productList: List<NewProductResponse>): RecyclerView.Adapter<NewProductListAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: SubscriptionMainNewproductItemBinding): RecyclerView.ViewHolder(binding.root) {
        val image = binding.newProductImage
        val productName = binding.productName
        val storeName = binding.storeName

        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SubscriptionMainNewproductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val productData = productList[position]

        Glide
            .with(holder.root.context)
            .load(productData.image)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(holder.image)

        holder.productName.text = productData.name
        holder.storeName.text = productData.brand
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}