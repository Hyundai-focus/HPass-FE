package com.hyundai.hpass.newProduct.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.newProduct.model.request.ApplyNewProdRequest
import com.hyundai.hpass.newProduct.model.response.NewItemListResponse
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
class NewProductViewModel:ViewModel() {
    val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    val newProductItems : MutableLiveData<List<NewItemListResponse>> = MutableLiveData()
    val userProdInfo : MutableLiveData<UsrProdStatusResponse> = MutableLiveData()
    val applyStatus : MutableLiveData<String> = MutableLiveData()
    val cancelStatus : MutableLiveData<String> = MutableLiveData()

    fun getProductList(){
        viewModelScope.launch {
            val prodRes = async(Dispatchers.IO){
                RetrofitClient.newProductService.getProdList(token)
            }.await()
            if(prodRes.isSuccessful){
                // stock이 0인 상품을 마지막으로 보내고, 날짜가 빠른 순으로 정렬
                val prodListResponse = prodRes.body()!!.sortedWith(compareBy({ it.stock == 0 }, { it.receiveDt }))
                newProductItems.postValue(prodListResponse)
            }
            else{
                errorMessage.postValue("신상품 목록 통신 실패: ${prodRes.code()}")
            }
        }
    }

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

    fun applyNewProd(prodNumber: ApplyNewProdRequest){
        viewModelScope.launch {
            val applyRes = async(Dispatchers.IO){
                RetrofitClient.newProductService.applyNewProd(token, prodNumber)
            }.await()
            if(applyRes.isSuccessful) {
                applyStatus.postValue(applyRes.body())
            }
            else errorMessage.postValue( "신청 통신 실패: ${applyRes.code()}")
        }
    }

    fun cancelProd(){
        viewModelScope.launch {
            val cancelRes = async(Dispatchers.IO){
                RetrofitClient.newProductService.cancelNewProd(token)
            }.await()
            if(cancelRes.isSuccessful) {
                cancelStatus.postValue(cancelRes.body())
            }
            else errorMessage.postValue( "신청 통신 실패: ${cancelRes.code()}")
        }
    }
}