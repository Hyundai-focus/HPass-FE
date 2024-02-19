package com.hyundai.hpass.socialLogIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.BuildConfig
import com.hyundai.hpass.BuildConfig.PREF_KEY_SUBS
import com.hyundai.hpass.BuildConfig.PREF_KEY_TOKEN
import com.hyundai.hpass.databinding.PurchaseTestActivityBinding
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.bio.BootpayBio
import kr.co.bootpay.bio.models.BioPayload
import kr.co.bootpay.bio.models.BioPrice
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
        binding.logoutBtn.setOnClickListener {
            MyApplication.preferences.setString(PREF_KEY_TOKEN,null)
            MyApplication.preferences.setString(PREF_KEY_SUBS, null)
            Log.d("LOGOUT: PREF_KEY_TOKEN:", MyApplication.preferences.getString(PREF_KEY_TOKEN)?:"삭제됨")
            Log.d("LOGOUT: PREF_KEY_SUBS:", MyApplication.preferences.getString(PREF_KEY_SUBS)?:"삭제됨")
            val intent = Intent(this@PurchaseTestActivity, SocialLoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
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
        var price = 500.0

        val payload = BioPayload()
        payload.setApplicationId(BuildConfig.bootpay_api_key)
            .setOrderName("HPASS 구독 정기 결제")
            .setOrderId("1234")
            .setPrice(price)
            .setPg("나이스페이")
            .setUserToken("65cea1f5e57a7e002335f025")
            .setPrices(
                listOf(BioPrice("구독 정기결제 (월)", 9900.0))
            )
            .setUser(user)
        BootpayBio.init(applicationContext)
            .setBioPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) { Log.d("bootpay", "cancel: $data") }
                override fun onError(data: String) {Log.d("bootpay", "error: $data")}
                override fun onClose() {
                    BootpayBio.removePaymentWindow()
                }
                override fun onIssued(data: String) { Log.d("bootpay", "issued: $data") }
                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay", "confirm: $data")
                    //                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)
                    return true //재고가 있어서 결제를 진행하려 할때 true (방법 2)
                    //                        return false; //결제를 진행하지 않을때 false
                }
                override fun onDone(data: String) {
                    Log.d("bootpayhschoi", "done: $data")
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
                    val jwtToken = MyApplication.preferences.getString(PREF_KEY_TOKEN)
                    Log.d("bootpayhschoi", "$payment+!!!")
                    viewModel.addSubscriber(jwtToken, payment)

                }
            }).requestPassword()
    }
}