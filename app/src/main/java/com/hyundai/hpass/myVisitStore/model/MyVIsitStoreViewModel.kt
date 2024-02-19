package com.hyundai.hpass.myVisitStore.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig.PREF_KEY_TOKEN
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김은서
 *
 */
class MyVIsitStoreViewModel: ViewModel()  {
    val token = MyApplication.preferences.getString(PREF_KEY_TOKEN)
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val storeList : MutableLiveData<List<StoreListResponse>> = MutableLiveData()

    fun getStoreList(){
        viewModelScope.launch {
            val storeRes = async(Dispatchers.IO){
                RetrofitClient.myVisitStoreService.getStoreList(token)
            }.await()
            if (storeRes.isSuccessful) storeList.postValue(storeRes.body()!!)
            else errorMessage.postValue("상점 목록 통신 실패: ${storeRes.code()}")
        }
    }
}