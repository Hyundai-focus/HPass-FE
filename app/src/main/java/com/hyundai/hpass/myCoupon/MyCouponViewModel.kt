package com.hyundai.hpass.myCoupon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyundai.hpass.myCoupon.model.response.CouponResponse

/**
 *
 * @author 김기훈
 *
 */
class MyCouponViewModel: ViewModel() {
    private val coupons: MutableLiveData<List<CouponResponse>> = MutableLiveData()

    init {
        loadCoupons()
        mockData()
    }

    fun getCoupon(): MutableLiveData<List<CouponResponse>> = coupons

    private fun loadCoupons() {

    }

    private fun mockData() {
        coupons.value = listOf(
            CouponResponse("1","스타벅스", "옷 상품 이름 및 대충 브랜드 이름 30% 할인", "02.01(목)", "02.07(수)"),
            CouponResponse("2","이디야", "어떤 화장품 브랜드 이름 바디워시 40% 할인", "02.01(목)", "02.07(수)"),
            CouponResponse("3","투썸플레이스", "앤 프렌즈 40% 할인", "02.1(목)", "02.07(수)"))
    }

}