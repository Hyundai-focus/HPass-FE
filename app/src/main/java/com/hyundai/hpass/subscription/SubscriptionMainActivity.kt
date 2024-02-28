package com.hyundai.hpass.subscription

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.SubscriptionActivityMainBinding
import com.hyundai.hpass.myBooking.MyBookingActivity
import com.hyundai.hpass.myCoupon.MyCouponActivity
import com.hyundai.hpass.myPage.MyPageActivity
import com.hyundai.hpass.myVisitStore.MyVisitStoreActivity
import com.hyundai.hpass.newProduct.NewProductActivity
import com.hyundai.hpass.popUpStore.PopUpStoreActivity

class SubscriptionMainActivity : AppCompatActivity() {

    private lateinit var binding: SubscriptionActivityMainBinding
    private lateinit var viewModel: SubscriptionMainViewModel
    private lateinit var popUpStoreListAdapter: PopUpStoreListAdapter
    private lateinit var todayStoreListAdapter: TodayStoreListAdapter
    private lateinit var newProductListAdapter: NewProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.light_green) //상태바 색깔
        viewModel = ViewModelProvider(this)[SubscriptionMainViewModel::class.java]
        binding = SubscriptionActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureUI()
        configureEvent()
        bind()
    }

    private fun configureUI() {
        binding.subscriptionMainButtonGroup.myReservation.buttonText.text = "나의 예약"
        binding.subscriptionMainButtonGroup.myReservation.buttonImage.setImageResource(R.drawable.subscription_my_reservation)
        binding.subscriptionMainButtonGroup.myCoupon.buttonText.text = "쿠폰 리스트"
        binding.subscriptionMainButtonGroup.myCoupon.buttonImage.setImageResource(R.drawable.subscription_my_coupon)
        binding.subscriptionMainButtonGroup.todayStore.buttonText.text = "오늘의 매장"
        binding.subscriptionMainButtonGroup.todayStore.buttonImage.setImageResource(R.drawable.subscription_today_store)
        binding.subscriptionMainButtonGroup.newProduct.buttonText.text = "이달의 신제품"
        binding.subscriptionMainButtonGroup.newProduct.buttonImage.setImageResource(R.drawable.subscription_new_product)
        binding.subscriptionMainButtonGroup.myPage.buttonText.text = "마이 페이지"
        binding.subscriptionMainButtonGroup.myPage.buttonImage.setImageResource(R.drawable.my_page_subs_icon)
    }

    private fun configureEvent() {

        binding.subscriptionMainButtonGroup.myReservation.root.setOnClickListener {
            val intent = Intent(this, MyBookingActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainButtonGroup.myCoupon.root.setOnClickListener {
            val intent = Intent(this, MyCouponActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainButtonGroup.todayStore.root.setOnClickListener {
            val intent = Intent(this, MyVisitStoreActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainButtonGroup.newProduct.root.setOnClickListener {
            val intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainButtonGroup.myPage.root.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainPopupGroup.popUpStoreMoreText.setOnClickListener {
            val intent = Intent(this, PopUpStoreActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainStoreGroup.todayStoreMoreText.setOnClickListener {
            val intent = Intent(this, MyVisitStoreActivity::class.java)
            startActivity(intent)
        }

        binding.subscriptionMainNewproductGroup.newProductMoreText.setOnClickListener {
            val intent = Intent(this, NewProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bind() {
        viewModel.getMemberName().observe(this) { memberName ->
            binding.subscriptionMainHpassGroup.userName.text = memberName + "님은"
            binding.subscriptionMainGreetingGroup.greetingMessage.text = memberName + "님 안녕하세요!"
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