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
            CouponResponse("스타벅스", "02.01(목)", "02.07(수)", "1층", "https://www.ehyundai.com/attachfiles/branch/20210209104434346.jpg"),
            CouponResponse("이디야", "02.01(목)", "02.07(수)", "2층", "https://www.ehyundai.com/attachfiles/branch/20220906035951614.jpg"),
            CouponResponse("투썸플레이스", "02.1(목)", "02.07(수)", "3층", "https://www.ehyundai.com/attachfiles/branch/20210209093808991.jpg")
        )
    }

}