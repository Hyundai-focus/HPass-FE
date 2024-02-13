package com.hyundai.hpass.socialLogIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.PurchaseTestActivityBinding
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload
import org.json.JSONObject

class PurchaseTestActivity : AppCompatActivity() {
    private lateinit var viewModel: PurchaseTestViewModel
    private lateinit var binding: PurchaseTestActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PurchaseTestActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[PurchaseTestViewModel::class.java]

        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.purchaseBtn.setOnClickListener {
            purchaseSubscribe()
        }
    }

    private fun bind() {
        viewModel.getSubsSuccess().observe(this) { success ->
            if(success) {
                Toast.makeText(this@PurchaseTestActivity, "구독 성공", Toast.LENGTH_SHORT)
            }
            else Toast.makeText(this@PurchaseTestActivity, "구독 실패..", Toast.LENGTH_SHORT)
        }
    }

    private fun purchaseSubscribe() {
        val user = BootUser().setPhone("010-1234-5678") // 구매자 정보
        val extra = BootExtra()
            .setCardQuota("0,2,3") // 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
        var price = 1000.0

        val payload = Payload()
        payload.setApplicationId(BuildConfig.bootpay_api_key)
            .setOrderName("부트페이 결제테스트")
            .setOrderId("1234")
            .setPrice(price)
            .setUser(user)
        Bootpay.init(supportFragmentManager, applicationContext)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) { Log.d("bootpay", "cancel: $data") }
                override fun onError(data: String) {Log.d("bootpay", "error: $data")}
                override fun onClose() {Bootpay.removePaymentWindow()}
                override fun onIssued(data: String) { Log.d("bootpay", "issued: $data") }
                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay", "confirm: $data")
                    //                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)
                    return true //재고가 있어서 결제를 진행하려 할때 true (방법 2)
                    //                        return false; //결제를 진행하지 않을때 false
                }
                override fun onDone(data: String) {
                    Log.d("bootpay", "done: $data")
                    val payment = JSONObject(data).getJSONObject("data").getString("method_origin")
                    val jwtToken = TokenManager.getToken(MyApplication.instance)!! // 이 한줄로 accessToken값 얻음
                    viewModel.addSubscriber(jwtToken, payment)
                }
            }).requestPayment()

    }
}