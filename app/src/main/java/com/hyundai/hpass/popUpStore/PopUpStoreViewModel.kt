package com.hyundai.hpass.popUpStore

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
 * @author 김기훈
 *
 */
class PopUpStoreViewModel: ViewModel() {

    private val popUpStore: MutableLiveData<List<PopUpStoreResponse>> = MutableLiveData()
    private val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)

    init {
        loadPopUpStore()
    }

    fun getPopUpStore(): MutableLiveData<List<PopUpStoreResponse>> = popUpStore

    private fun loadPopUpStore() {
        viewModelScope.launch {
            val popUpRes = async(Dispatchers.IO) {
                RetrofitClient.popUpStoreService.getAllPopUpStore(token)
            }.await()

            if (popUpRes.isSuccessful && popUpRes.body() != null) {
                popUpStore.postValue(popUpRes.body())
            }
        }
    }
}