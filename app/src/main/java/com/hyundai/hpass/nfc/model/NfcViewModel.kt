package com.hyundai.hpass.nfc.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.myVisitStore.model.response.StoreListResponse
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.newProduct.model.response.UsrProdStatusResponse
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 김은서
 *
 */
class NfcViewModel : ViewModel() {
    val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val userProdInfo : MutableLiveData<UsrProdStatusResponse> = MutableLiveData()
    val userVistiNum : MutableLiveData<Long> = MutableLiveData()
    val uservisitRes : MutableLiveData<StoreListResponse> = MutableLiveData()

    fun getUsrProdInfo(){
        viewModelScope.launch {
            val userProd = async(Dispatchers.IO){
                RetrofitClient.newProductService.usrProductApplyInfo(token)
            }.await()
            if(userProd.isSuccessful) {
                userProdInfo.postValue(userProd.body()!!)
            }
            else errorMessage.postValue( "유저 신청 정보 통신 실패: ${userProd.code()}")
        }
    }
    fun getUsrVisitNum(){
        viewModelScope.launch {
            val numRes = async(Dispatchers.IO){
                RetrofitClient.nfcService.visitNum(token)
            }.await()
            if(numRes.isSuccessful) {
                userVistiNum.postValue(numRes.body()!!)
            }
            else errorMessage.postValue( "유저 방문 개수 통신 실패: ${numRes.code()}")
        }
    }
    fun getVisitInfo(storeNo : Long){
        viewModelScope.launch {
            val storeRes = async(Dispatchers.IO){
                RetrofitClient.nfcService.visitiStore(storeNo, token)
            }.await()
            Log.d("storeRes", storeRes.body().toString())
            if(storeRes.isSuccessful) {
                uservisitRes.postValue(storeRes.body()!!)
            }
            else errorMessage.postValue( "유저 방문 통신 실패: ${storeRes.code()}")
        }
    }
}