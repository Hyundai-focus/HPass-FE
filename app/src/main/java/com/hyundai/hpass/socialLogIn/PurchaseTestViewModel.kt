package com.hyundai.hpass.socialLogIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 *
 * @author 최현서
 *
 */
class PurchaseTestViewModel: ViewModel() {
    private val subsSuccess: MutableLiveData<Boolean> = MutableLiveData()
    fun getSubsSuccess(): LiveData<Boolean> = subsSuccess
    fun addSubscriber(jwtToken: String, payment: String) {
        viewModelScope.launch{
            val subsRes = async(Dispatchers.IO) {
                RetrofitClient.memberService.addSubscriber(jwtToken, payment)
            }.await()

            if (subsRes.isSuccessful){
                subsSuccess.postValue(true)
            }
            else {
                subsSuccess.postValue(false)
            }
        }
    }
}