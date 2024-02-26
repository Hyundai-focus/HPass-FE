package com.hyundai.hpass.subscription

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse
import com.hyundai.hpass.socialLogIn.MyApplication
import com.hyundai.hpass.subscription.model.response.PopUpStoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김기훈
 *
 */
class SubscriptionMainViewModel: ViewModel() {

    private var token: String

    private val memberName: MutableLiveData<String> = MutableLiveData()
    private val popUpStore: MutableLiveData<List<PopUpStoreResponse>> = MutableLiveData()
    private val todayStore: MutableLiveData<List<StoreListResponse>> = MutableLiveData()
    private val newProduct: MutableLiveData<List<NewItemListResponse>> = MutableLiveData()

    init {
        token = MyApplication.preferences.getString("Authorization")
        loadUser()
        loadPopUpStore()
        loadTodayStore()
        loadNewProduct()
    }

    fun getMemberName(): MutableLiveData<String> = memberName
    fun getPopUpStore(): MutableLiveData<List<PopUpStoreResponse>> = popUpStore
    fun getTodayStore(): MutableLiveData<List<StoreListResponse>> = todayStore
    fun getNewProduct(): MutableLiveData<List<NewItemListResponse>> = newProduct

    private fun loadUser() {
        memberName.postValue(MyApplication.preferences.getString("memberName"))
    }

    private fun loadPopUpStore() {
        viewModelScope.launch {
            val popUpRes = async(Dispatchers.IO) {
                RetrofitClient.popUpStoreService.getAllPopUpStore(token)
            }.await()

            popUpRes?.let {
                if (popUpRes.isSuccessful) {
                    popUpStore.postValue(popUpRes.body())
                } else {
                    Log.d("SubscriptionMainViewModel", "loadPopUpStore: ${popUpRes.message()}")
                }
            }
        }
    }

    private fun loadTodayStore() {
        viewModelScope.launch {
            val todayRes = async(Dispatchers.IO) {
                RetrofitClient.myVisitStoreService.getStoreList(token)
            }.await()

            todayRes?.let {
                if (todayRes.isSuccessful) {
                    todayStore.postValue(todayRes.body())
                } else {
                    Log.d("SubscriptionMainViewModel", "loadTodayStore: ${todayRes.message()}")
                }
            }
        }
    }

    private fun loadNewProduct() {
        viewModelScope.launch {
            val newProductRes = async(Dispatchers.IO) {
                RetrofitClient.newProductService.getProdList(token)
            }.await()

            newProductRes?.let {
                if (newProductRes.isSuccessful) {
                    newProduct.postValue(newProductRes.body())
                } else {
                    Log.d("SubscriptionMainViewModel", "loadNewProduct: ${newProductRes.message()}")
                }
            }
        }
    }
}