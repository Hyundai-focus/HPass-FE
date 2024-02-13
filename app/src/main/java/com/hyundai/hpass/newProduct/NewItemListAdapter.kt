package com.hyundai.hpass.newProduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hyundai.hpass.databinding.NewProductItemNewProductBinding
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse

/**
 *
 * @author 김은서
 *
 */
class NewItemListAdapter(private val fragmentManager: FragmentManager) :RecyclerView.Adapter<NewItemListAdapter.NewItemListViewHolder>(){
    class NewItemListViewHolder(val binding: NewProductItemNewProductBinding) : RecyclerView.ViewHolder(binding.root)

    //더미 아이템
    private val items:List<NewItemListResponse> = listOf(
        NewItemListResponse("Re X Re", "리바이리 핸드크림 & 워시 듀오", "https://imgprism.ehyundai.com/evntCrdInf/imgPath/202401/31/4ac53dd0-33ff-477c-a5c1-2fbe41ac6a48.png", true),
        NewItemListResponse("모두의 맛집", "낫배드 스파이시 삼겹살 파스타", "https://www.ehyundai.com/attachfiles/branch/20210209095357243.jpg", true),
        NewItemListResponse("Re X Re", "리바이리 핸드크림 & 워시 듀오", "https://imgprism.ehyundai.com/img2ItemNmPrcTypeInf/imgPath2/202402/06/9a72e909-a8e0-4ce8-8685-df5741ce5563.png", true),
        NewItemListResponse("리바트", "에스테틱 화장대 서랍 칸막이", "https://imgprism.ehyundai.com/evntCrdInf/imgPath/202401/29/8bafd8cb-c812-4844-b960-40e4b53feb0f.png", true),
        NewItemListResponse("리바트", "에스테틱 화장대 서랍 칸막이", "https://imgprism.ehyundai.com/evntCrdInf/imgPath/202401/29/8bafd8cb-c812-4844-b960-40e4b53feb0f.png", false),
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemListViewHolder {
        val binding = NewProductItemNewProductBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NewItemListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NewItemListViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.img)
                .into(newProductListImg)
            newProductListTitle.text = item.title
            newProductListSubtitle.text = item.subTitle
            if(!item.status){
                newProductListStatusFalse.visibility = View.VISIBLE
                newProductListStatusTrue.visibility = View.INVISIBLE
            }else{ //신청 완료 시에는 클릭 불가
                newProductItem.setOnClickListener{
                    //newProductDialog(item, user.status)
                    newProductDialog(item, false) //뷰모델 유저 상태 받아서 현재 아이템 히스토리 있는지 없는지 따라서 다이얼로그 보여주기
                }
            }

        }
    }
    fun newProductDialog(item : NewItemListResponse, status : Boolean){
        if(status){ //이미 선택 한 경우
            val dialogFragment = ImpossibleNewProductDialog()
            dialogFragment.show(fragmentManager, "ImpossibleNewProductDialogTag")
        }
        else{
            val dialogFragment = ApplyNewProductDialog.newInstance(item)
            dialogFragment.show(fragmentManager, "ApplyNewProductDialogTag")
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }

}