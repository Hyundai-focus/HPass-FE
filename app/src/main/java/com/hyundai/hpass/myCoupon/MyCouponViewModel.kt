package com.hyundai.hpass.myCoupon

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myCoupon.model.response.MyCouponResponse
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김기훈
 *
 */
class MyCouponViewModel : ViewModel() {

    private val token: String

    private val coupons: MutableLiveData<List<MyCouponResponse>> = MutableLiveData()

    init {
        token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        loadCoupons()
    }

    fun getCoupon(): MutableLiveData<List<MyCouponResponse>> = coupons

    private fun loadCoupons() {
        viewModelScope.launch {
            val numRes = async(Dispatchers.IO) {
                RetrofitClient.myCouponService.getMyAllCoupon(token)
            }.await()

            numRes.body()?.let {
                coupons.postValue(it)
            }
        }
    }
}