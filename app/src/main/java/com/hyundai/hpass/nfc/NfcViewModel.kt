package com.hyundai.hpass.nfc

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김은서
 *
 */
class NfcViewModel : ViewModel() {
    private val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)

    private val popUpStore: MutableLiveData<PopUpStoreResponse?> = MutableLiveData()
    private val visitStore: MutableLiveData<Boolean> = MutableLiveData()
    private val coupon: MutableLiveData<Boolean> = MutableLiveData()
    private val isHPass: MutableLiveData<Boolean> = MutableLiveData()

    fun getPopUpStore() = popUpStore
    fun getVisitStore() = visitStore
    fun getCoupon() = coupon
    fun getIsHPass() = isHPass

    fun fetchData(uri: Uri) {
        val type = uri.getQueryParameter("type")

        if (type == "popupstore") {
            checkPopUpStore(uri.getQueryParameter("popupstoreno"))
        } else if (type == "store") {
            visitStore(uri.getQueryParameter("storeno"))
        } else if (type == "coupon") {
            issueCoupon(uri.getQueryParameter("couponno"))
        } else if (type == "subscription") {
            isHPass.postValue(true)
        }
    }

    private fun checkPopUpStore(popupStoreNo: String?) {
        viewModelScope.launch {
            val popUpRes = async(Dispatchers.IO) {
                popupStoreNo?.let {
                    RetrofitClient.nfcService.getPopupBooking(it.toLong(), token)
                }
            }.await()

            popUpRes?.let {
                if (popUpRes.isSuccessful) {
//                    popUpStore.postValue()
                } else {
                    popUpStore.postValue(null)
                }
            }
        }
    }

    private fun visitStore(storeNo: String?) {
        viewModelScope.launch {
            val numRes = async(Dispatchers.IO) {
                storeNo?.let {
                    RetrofitClient.nfcService.visitStore(it.toLong(), token)
                }
            }.await()

            numRes?.let {
                if (numRes.isSuccessful) {
                    visitStore.postValue(true)
                } else {
                    visitStore.postValue(false)
                }
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

            couponRes?.let {
                if (couponRes.isSuccessful) {
                    coupon.postValue(true)
                } else {
                    coupon.postValue(false)
                }
            }
        }
    }
}