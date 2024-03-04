package com.hyundai.hpass.newProduct.model

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


// 작성자: 김은서
// 기능: 신상품 체험 뷰모델
class NewProductViewModel : ViewModel() {
    private val token = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)

    val newProductItems: MutableLiveData<List<NewItemListResponse>> = MutableLiveData()
    val userProdInfo: MutableLiveData<UsrProdStatusResponse> = MutableLiveData()
    val applyStatus: MutableLiveData<String> = MutableLiveData()
    val cancelStatus: MutableLiveData<String> = MutableLiveData()


    // 작성자: 김은서
    // 기능: 신상품 목록 통신
    fun getProductList() {
        viewModelScope.launch {
            val prodRes = async(Dispatchers.IO) {
                RetrofitClient.newProductService.getProdList(token)
            }.await()

            if (prodRes.isSuccessful) {
                // stock이 0인 상품을 마지막으로 보내고, 날짜가 빠른 순으로 정렬
                val prodListResponse =
                    prodRes.body()!!.sortedWith(compareBy({ it.stock == 0 }, { it.receiveDt }))
                newProductItems.postValue(prodListResponse)
            }
        }
    }

    // 작성자: 김은서
    // 기능: 유저 신청 정보 통신
    fun getUsrProdInfo() {
        viewModelScope.launch {
            val userProd = async(Dispatchers.IO) {
                RetrofitClient.newProductService.usrProductApplyInfo(token)
            }.await()

            if (userProd.isSuccessful) {
                userProdInfo.postValue(userProd.body()!!)
            }
        }
    }

    // 작성자: 김은서
    // 기능: 신상품 신청하기 통신
    fun applyNewProd(prodNumber: ApplyNewProdRequest) {
        viewModelScope.launch {
            val applyRes = async(Dispatchers.IO) {
                RetrofitClient.newProductService.applyNewProd(token, prodNumber)
            }.await()

            if (applyRes.isSuccessful) {
                applyStatus.postValue(applyRes.body())
            }
        }
    }

    // 작성자: 김은서
    // 기능: 신상품 신청 취소 통신
    fun cancelProd() {
        viewModelScope.launch {
            val cancelRes = async(Dispatchers.IO) {
                RetrofitClient.newProductService.cancelNewProd(token)
            }.await()
            
            if (cancelRes.isSuccessful) {
                cancelStatus.postValue(cancelRes.body())
            }
        }
    }
}