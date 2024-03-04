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

// 작성자: 김은서
// 기능: 오늘의 상점 뷰모델
class MyVIsitStoreViewModel: ViewModel()  {
    val token = MyApplication.preferences.getString(PREF_KEY_TOKEN)
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val storeList : MutableLiveData<List<StoreListResponse>> = MutableLiveData()
    val floorList : MutableLiveData<List<Long>> = MutableLiveData()

    // 작성자: 김은서
    // 기능: 오늘의 상점 목록 통신
    fun getStoreList(){
        viewModelScope.launch {
            val storeRes = async(Dispatchers.IO){
                RetrofitClient.myVisitStoreService.getStoreList(token)
            }.await()
            if (storeRes.isSuccessful && storeRes.body()?.isEmpty() == false) {
                val sortedStoreList = storeRes.body()!!.sortedBy { it.storeFloor.firstOrNull { it.isDigit() } }
                storeList.postValue(sortedStoreList)
            } else {
                errorMessage.postValue("상점 목록 통신 실패: ${storeRes.code()}")
            }
        }
    }

    // 작성자: 김은서
    // 기능: 방문한 오늘의 상점 층수 목록 통신
    fun getFloorList(){
        viewModelScope.launch {
            val floorRes = async(Dispatchers.IO){
                RetrofitClient.myVisitStoreService.getFloorList(token)
            }.await()
            if (floorRes.isSuccessful && floorRes.body()?.isEmpty() == false) {
                floorList.postValue(floorRes.body())
            } else {
                errorMessage.postValue("방문 목록 통신 실패: ${floorRes.code()}")
            }
        }
    }
}