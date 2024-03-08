package com.hyundai.hpass.subscription

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.databinding.AddSubscriptionFragmentInfoBinding
import com.hyundai.hpass.network.RetrofitClient
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.bio.BootpayBio
import kr.co.bootpay.bio.models.BioPayload
import kr.co.bootpay.bio.models.BioPrice
import org.json.JSONObject
import java.time.LocalDate

// 작성자: 최현서
// 기능: 부트페이 결제 및 구독 등록
class AddSubscriptionInfoViewModel : ViewModel() {
    private var userToken: MutableLiveData<String?> = MutableLiveData()
    private val subsSuccess: MutableLiveData<String> = MutableLiveData()
    fun getUserToken(): LiveData<String?> = userToken
    fun getSubsSuccess(): LiveData<String> = subsSuccess

    fun calculatePaymentDate(startDate: LocalDate, status: Int): String {
        val nextDate = startDate.plusMonths(1)
        val nextPaymentDate = if (nextDate.dayOfMonth < startDate.dayOfMonth) {
            nextDate.withDayOfMonth(nextDate.lengthOfMonth())
        } else {
            nextDate.withDayOfMonth(startDate.dayOfMonth)
        }
        return if (status == 1) nextPaymentDate.toString() else "$startDate ~ $nextPaymentDate"
    }

    fun getBootPayToken() {
        val accessToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        viewModelScope.launch{
            val bootTokenRes = async(Dispatchers.IO) {
                RetrofitClient.subscriptionService.getPaymentToken(accessToken)
            }.await()
            if (bootTokenRes.isSuccessful) {
                val bootTokenResponse = bootTokenRes.body()
                if (bootTokenResponse != null) {
                    Log.d("getBootPayToken Retrofit 통신:", "성공: $bootTokenResponse")
                    userToken.postValue(bootTokenRes.body())
                }
                else Log.d("getBootPayToken Retrofit 통신:", "실패: Response null")
            }
            else Log.d("getBootPayToken Retrofit 통신:", "실패: 통신 실패")
        }
    }

    fun purchaseSubscribe(phoneNum: String, userToken: String, context: Context) {
        val user = BootUser().setPhone(phoneNum)
        val price = 4500.0

        val payload = BioPayload()
        payload.setApplicationId(BuildConfig.bootpay_api_key)
            .setOrderName("HPASS 구독 정기 결제")
            .setOrderId("1234")
            .setPrice(price)
            .setPg("나이스페이")
            .setUserToken(userToken)
            .setPrices(listOf(BioPrice("구독 정기결제 (월)", 4500.0)))
            .setUser(user)
        BootpayBio.init(context)
            .setBioPayload(payload)
            .setEventListener(object: BootpayEventListener{
                override fun onCancel(data: String) { Log.d("bootPay 결제", "cancel: $data") }
                override fun onError(data: String) {Log.d("bootPay 결제", "error: $data")}
                override fun onClose() { BootpayBio.removePaymentWindow() }
                override fun onIssued(data: String) { Log.d("bootPay 결제", "issued: $data") }
                override fun onConfirm(data: String): Boolean {
                    Log.d("bootPay 결제", "confirm: $data")
                    return true
                }
                override fun onDone(data: String) {
                    Log.d("bootPay 결제", "done: $data")
                    var payment: String
                    try {
                        payment = JSONObject(data).getJSONObject("card_data").getString("card_company") + "카드"
                    } catch (e: Exception) {
                        try {
                            payment = JSONObject(data).getJSONObject("data").getString("method_origin")
                        } catch (e: Exception) {
                            payment = "결제 정보를 찾을 수 없습니다."
                        }
                    }
                    Log.d("bootPay 결제 수단", "payment: $payment")
                    addSubscriber(payment)
                }
            }).requestPassword()
    }

    fun addSubscriber(payment: String) {
        val jwtToken = MyApplication.preferences.getString(BuildConfig.PREF_KEY_TOKEN)
        viewModelScope.launch{
            val subsRes = async(Dispatchers.IO) {
                RetrofitClient.subscriptionService.addSubscriber(jwtToken, payment)
            }.await()
            if (subsRes.isSuccessful){
                subsSuccess.postValue(payment)
            }
            else {
                subsSuccess.postValue("")
            }
        }
    }
}