package com.hyundai.hpass.myVisitStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.databinding.MyVisitStoreStoreBinding
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse

// 작성자: 김은서
// 기능: 오늘의 상점 리사이클러뷰 어댑터
class StoreListAdapter(
    private val items: List<StoreListResponse>,
    private val fragmentManager: FragmentManager
    ) : RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder>() {

    class StoreListViewHolder(val binding: MyVisitStoreStoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        val binding = MyVisitStoreStoreBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StoreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.storeImg)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(myVisitStoreImage)
            myVisitStoreFloor.text = item.storeFloor
            myVisitStoreTitle.text = item.storeBrand
            if(item.visitStatus) myVisitStoreStoreStatus.visibility = View.VISIBLE
            storeItem.setOnClickListener{
                mapDialog(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun mapDialog(item :StoreListResponse){
        val dialog = StoreMapDialog.newInstance(item)
        dialog.show(fragmentManager, "StoreMapDialogTag")
    }
}