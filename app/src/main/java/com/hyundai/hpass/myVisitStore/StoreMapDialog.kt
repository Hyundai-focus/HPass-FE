package com.hyundai.hpass.myVisitStore

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.MyVisitStoreDialogMapBinding
import com.hyundai.hpass.myVisitStore.model.MyVIsitStoreViewModel
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse

// 작성자: 김은서
// 기능: 오늘의 상점 지도 다이얼로그
class StoreMapDialog() : DialogFragment() {
    private lateinit var context: Context
    private lateinit var binding: MyVisitStoreDialogMapBinding
    private lateinit var viewModel: MyVIsitStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MyVIsitStoreViewModel::class.java]
    }
    override fun onStart() { //레이아웃 설정
        super.onStart()
        dialog?.window?.let { window ->
            window.setBackgroundDrawableResource(R.drawable.shape_corner_round) // 둥근 모서리 배경 설정
            val displayMetrics = context.resources?.displayMetrics //화면 크기 가져와서 높이 너비 설정
            val width = displayMetrics?.widthPixels ?: ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val widthMargin = (width * 0.1).toInt() //마진
            val params = window.attributes
            params.width = width - 2 * widthMargin // 좌우 마진 적용
            params.height = height
            params.gravity = Gravity.CENTER
            window.attributes = params
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MyVisitStoreDialogMapBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MyVIsitStoreViewModel::class.java]
        context = requireContext()
        val item = arguments?.getSerializable("item",StoreListResponse::class.java)
        item?.let {
            setDialogUI(binding, it)
        }
        binding.storeMapButton.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
    private fun setDialogUI(binding: MyVisitStoreDialogMapBinding, item: StoreListResponse) {
        Glide.with(this)
            .load(item.storeMap)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(binding.storeMapImg)
        binding.storeMapFloor.text = item.storeFloor
    }

    companion object {
        fun newInstance(item: StoreListResponse): StoreMapDialog {
            val args = Bundle().apply {
                putSerializable("item", item)
            }
            return StoreMapDialog().apply {
                arguments = args
            }
        }
    }
}