package com.hyundai.hpass.nfc

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.nfc.model.PopUpBookingResponse
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김기훈
 *
 */
class NfcViewModel : ViewModel() {
    private val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)

    private val popUpStore: MutableLiveData<PopUpBookingResponse?> = MutableLiveData()
    private val visitStore: MutableLiveData<StoreListResponse> = MutableLiveData()
    private val coupon: MutableLiveData<String> = MutableLiveData()
    private val isHPass: MutableLiveData<Boolean> = MutableLiveData()
    private val isSuccessVisitFiveStore: MutableLiveData<Boolean> = MutableLiveData(false)
    private val promotionCoupon: MutableLiveData<String> = MutableLiveData()

    fun getPopUpStore() = popUpStore
    fun getVisitStore() = visitStore
    fun getCoupon() = coupon
    fun getIsHPass() = isHPass
    fun getIsSuccessVisitFiveStore() = isSuccessVisitFiveStore
    fun getPromotionCoupon() = promotionCoupon

    fun fetchData(uri: Uri) {
        when (uri.getQueryParameter("type")) {
            "popupstore" -> {
                checkPopUpStore(uri.getQueryParameter("popupstoreno"))
            }

            "store" -> {
                visitStore(uri.getQueryParameter("storeno"))
            }

            "coupon" -> {
                issueCoupon(uri.getQueryParameter("couponno"))
            }

            "subscription" -> {
                isHPass.postValue(true)
            }
        }
    }

    private fun checkPopUpStore(popupStoreNo: String?) {
        viewModelScope.launch {
            val popUpRes = async(Dispatchers.IO) {
                popupStoreNo?.let {
                    RetrofitClient.nfcService.getPopupBooking(it.toLong(), token)
                }
            }.await()

            if (popUpRes?.isSuccessful == true && popUpRes.body() != null) {
                popUpStore.postValue(popUpRes.body())
            } else {
                popUpStore.postValue(null)
            }
        }
    }

    private fun visitStore(storeNo: String?) {
        viewModelScope.launch {
            val visitRes = async(Dispatchers.IO) {
                storeNo?.let {
                    RetrofitClient.nfcService.visitStore(it.toLong(), token)
                }
            }.await()

            if (visitRes?.isSuccessful == true && visitRes.body() != null) {

                visitRes.body()?.let {
                    if (it.storeFloor == "already" || it.storeFloor == "not today") {
                        visitStore.postValue(it)
                    } else if (it.visitStatus) {
                        // 오늘의 매장 방문 성공
                        issueCouponByStore(storeNo, it)

                        val visitStoreCountRes = async(Dispatchers.IO) {
                            RetrofitClient.nfcService.visitNum(token)
                        }.await()

                        if (visitStoreCountRes.isSuccessful
                            && visitStoreCountRes.body() != null
                            && visitStoreCountRes.body() == 5L
                        ) {
                            isSuccessVisitFiveStore.postValue(true)
                        } else {
                            isSuccessVisitFiveStore.postValue(false)
                        }
                    }
                }
            }
        }
    }

    private fun issueCouponByStore(storeNo: String?, storeListResponse: StoreListResponse) {
        viewModelScope.launch {
            val couponRes = async(Dispatchers.IO) {
                storeNo?.let {
                    RetrofitClient.nfcService.issueCouponByStore(it.toLong(), token)
                }
            }.await()

            if (couponRes?.isSuccessful == true && couponRes.body() != null) {
                visitStore.postValue(storeListResponse)
            }
        }
    }

    private fun issueCoupon(couponNo: String?) {
        viewModelScope.launch {
            val couponRes = async(Dispatchers.IO) {
                couponNo?.let {
                    RetrofitClient.nfcService.issueCoupon(it.toLong(), token)
                }
            }.await()

            if (couponRes?.isSuccessful == true && couponRes.body() != null) {
                coupon.postValue(couponRes.body().toString())
            }
        }
    }

    fun issuePromotionCoupon() {
        viewModelScope.launch {
            val promotionCouponRes = async(Dispatchers.IO) {
                RetrofitClient.nfcService.issueCoupon(0, token)
            }.await()

            if (promotionCouponRes.isSuccessful && promotionCouponRes.body() != null) {
                promotionCoupon.postValue(promotionCouponRes.body().toString())
            }
        }
    }
}