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
class StoreListAdapter : RecyclerView.Adapter<StoreListAdapter.StoreListViewHolder>() {
    class StoreListViewHolder(val binding: MyVisitStoreStoreBinding) : RecyclerView.ViewHolder(binding.root)
    //통신을 통해 받아올 데이터 -> 각 층, 현재 체크 되어있는지 안되어있는지
    private val items: List<StoreListResponse> = listOf(
        StoreListResponse("1F Exclusive Label", "https://www.ehyundai.com/images/webhome2/store/img_the_hyundai_seoul_floor_1f.png", "1층매장이름",true),
        StoreListResponse("2F Modern Mood", "https://www.ehyundai.com/images/webhome2/store/img_the_hyundai_seoul_floor_2f.png", "2층매장이름",true),
        StoreListResponse("3F About Fashion", "https://www.ehyundai.com/images/webhome2/store/img_the_hyundai_seoul_floor_3f.png", "3층매장이름",false),
        StoreListResponse("4F Life & Balance", "https://www.ehyundai.com/images/webhome2/store/img_the_hyundai_seoul_floor_4f.png", "4층매장이름",true),
        StoreListResponse("5F Sounds Forest", "https://www.ehyundai.com/images/webhome2/store/img_the_hyundai_seoul_floor_5f.png", "5층매장이름",false)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreListViewHolder {
        val binding = MyVisitStoreStoreBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return StoreListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreListViewHolder, position: Int) {
        val item = items[position] // 데이터 리스트에서 해당 위치의 데이터를 가져옵니다.
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.img)
                .into(myVisitStoreImage)
            myVisitStoreFloor.text = item.floor
            myVisitStoreTitle.text = item.title
            if(item.status) myVisitStoreStoreStatus.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}