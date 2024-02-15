package com.hyundai.hpass.subscription

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hyundai.hpass.databinding.SubscriptionActivityMainBinding
import com.hyundai.hpass.popUpStore.PopUpStoreActivity

class SubscriptionMainActivity : AppCompatActivity() {

    private lateinit var binding: SubscriptionActivityMainBinding
    private lateinit var viewModel: SubscriptionMainViewModel
    private lateinit var popUpStoreListAdapter: PopUpStoreListAdapter
    private lateinit var todayStoreListAdapter: TodayStoreListAdapter
    private lateinit var newProductListAdapter: NewProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SubscriptionMainViewModel::class.java]
        binding = SubscriptionActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
        bind()
    }

    private fun configureUI() {
        binding.subscriptionMainButtonGroup.myReservation.buttonText.text = "나의 예약"
        binding.subscriptionMainButtonGroup.myCoupon.buttonText.text = "쿠폰 리스트"
        binding.subscriptionMainButtonGroup.todayStore.buttonText.text = "오늘의 매장"
        binding.subscriptionMainButtonGroup.newProduct.buttonText.text = "이달의 신제품"
        binding.subscriptionMainButtonGroup.myPage.buttonText.text = "마이 페이지"
    }

    private fun configureEvent() {

        binding.subscriptionMainPopupGroup.popUpStoreMoreText.setOnClickListener {
            val intent = Intent(this, PopUpStoreActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainStoreGroup.todayStoreMoreText.setOnClickListener {

        }

        binding.subscriptionMainNewproductGroup.newProductMoreText.setOnClickListener {

        }
    }

    private fun bind() {
        viewModel.getUser().observe(this) { user ->
            binding.subscriptionMainHpassGroup.userName.text = user.name + "님은"
            binding.subscriptionMainGreetingGroup.greetingMessage.text = user.name + "님 안녕하세요!"
        }

        viewModel.getPopUpStore().observe(this) { popUpStoreList ->
            popUpStoreListAdapter = PopUpStoreListAdapter(popUpStoreList)
            binding.subscriptionMainPopupGroup.popUpStoreList.adapter = popUpStoreListAdapter
            binding.subscriptionMainPopupGroup.popUpStoreList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            val popUpStoreSnapHelper = PagerSnapHelper()
            popUpStoreSnapHelper.attachToRecyclerView(binding.subscriptionMainPopupGroup.popUpStoreList)
        }

        viewModel.getTodayStore().observe(this) { todayStoreList ->
            todayStoreListAdapter = TodayStoreListAdapter(todayStoreList)
            binding.subscriptionMainStoreGroup.todayStoreList.adapter = todayStoreListAdapter
            binding.subscriptionMainStoreGroup.todayStoreList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            val todayStoreSnapHelper = PagerSnapHelper()
            todayStoreSnapHelper.attachToRecyclerView(binding.subscriptionMainStoreGroup.todayStoreList)
        }

        viewModel.getNewProduct().observe(this) { newProductList ->
            newProductListAdapter = NewProductListAdapter(newProductList)
            binding.subscriptionMainNewproductGroup.newProductList.adapter = newProductListAdapter
            binding.subscriptionMainNewproductGroup.newProductList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            val newProductSnapHelper = PagerSnapHelper()
            newProductSnapHelper.attachToRecyclerView(binding.subscriptionMainNewproductGroup.newProductList)
        }
    }
}