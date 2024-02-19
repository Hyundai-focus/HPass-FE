package com.hyundai.hpass.newProduct

import android.content.Context
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
class NewItemListAdapter(
    private val status:Boolean,
    private val items : List<NewItemListResponse>,
    private val fragmentManager: FragmentManager
    ) :RecyclerView.Adapter<NewItemListAdapter.NewItemListViewHolder>(){
    class NewItemListViewHolder(val binding: NewProductItemNewProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewItemListViewHolder {
        val binding = NewProductItemNewProductBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return NewItemListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: NewItemListViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.productImg)
                .into(newProductListImg)
            newProductListTitle.text = item.productBrand
            newProductListSubtitle.text = item.productName
            if(item.stock == 0){
                newProductListStatusFalse.visibility = View.VISIBLE
                newProductListStatusTrue.visibility = View.INVISIBLE
            }else{ //신청 완료 시에는 클릭 불가
                newProductItem.setOnClickListener{
                    newProductDialog(item, status)
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