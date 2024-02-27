package com.hyundai.hpass.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hyundai.hpass.BuildConfig.BASE_URL
import com.hyundai.hpass.Websocket.WebSocketClient
import com.hyundai.hpass.databinding.NfcActivityMainBinding
import com.hyundai.hpass.main.MainViewModel
import com.hyundai.hpass.socialLogIn.MyApplication


class NfcMainActivity : AppCompatActivity() {

    private lateinit var binding: NfcActivityMainBinding
    private lateinit var nfcViewModel: NfcViewModel
    private lateinit var mainViewModel: MainViewModel
    private val webSocketClient = WebSocketClient(BASE_URL)
    //private val webSocketClient = WebSocketClient("ws://10.0.2.2:8080/socket/NfcCall")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NfcActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nfcViewModel = ViewModelProvider(this)[NfcViewModel::class.java]
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        webSocketClient.start()
        configureEvent()
        bind()
    }

    private fun configureEvent() {
        binding.confirmButton.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        mainViewModel.getLoginPass().observe(this) { pass ->
            if (pass == false) {
                binding.infoText.text = "로그인이 필요합니다"
                binding.infoSubtext.text = "로그인해주세요!"
                binding.lottieFail.visibility = View.VISIBLE
            }
        }

        mainViewModel.getIsSubscribed().observe(this) { isSubscribed ->
            if (isSubscribed == false) {
                binding.infoText.text = "HPass 구독자가 아닙니다"
                binding.infoSubtext.text = "구독자만 이용할 수 있는 서비스입니다"
                binding.lottieFail.visibility = View.VISIBLE
            } else {
                handleIntent(intent)
            }
        }

        nfcViewModel.getPopUpStore().observe(this) { popUpStore ->
                if (popUpStore == null) {
                    binding.infoText.text = "팝업스토어 예약 내역이 없습니다"
                    binding.infoSubtext.text = "팝업스토어 예약 먼저 해주세요!"
                    binding.lottieFail.visibility = View.VISIBLE
                } else {
                    binding.infoText.text = "예약 내역이 확인되었습니다"
                    binding.infoSubtext.text = "지금 바로 입장해주세요!"
                    binding.lottieCheck.visibility = View.VISIBLE
                }
        }

        nfcViewModel.getVisitStore().observe(this) { visitStore ->
            if (visitStore == false) {
                binding.infoText.text = "방문 내역이 없습니다"
                binding.infoSubtext.text = "매장을 방문해주세요!"
                binding.lottieFail.visibility = View.VISIBLE
            } else {
                binding.infoText.text = "방문 내역이 확인되었습니다"
                binding.infoSubtext.text = "지금 바로 이용해주세요!"
                binding.lottieCheck.visibility = View.VISIBLE
            }
        }

        nfcViewModel.getCoupon().observe(this) { coupon ->
            if (coupon == false) {
                binding.infoText.text = "쿠폰 발급에 실패했습니다"
                binding.infoSubtext.text = "다시 시도해주세요!"
                binding.lottieFail.visibility = View.VISIBLE
            } else {
                binding.infoText.text = "쿠폰이 발급되었습니다"
                binding.infoSubtext.text = "마이페이지에서 확인해주세요!"
                binding.lottieCheck.visibility = View.VISIBLE
            }
        }

        nfcViewModel.getIsHPass().observe(this) { isHPass ->
            if (isHPass == false) {
                binding.infoText.text = "HPass 구독자가 아닙니다"
                binding.infoSubtext.text = "구독자만 이용할 수 있는 서비스입니다"
                binding.lottieFail.visibility = View.VISIBLE
            } else {
                binding.infoText.text = "구독 정보가 확인되었습니다"
                binding.infoSubtext.text = "할인된 가격으로 즐기세요!"
                binding.lottieCheck.visibility = View.VISIBLE
                webSocketClient.sendMessage("member::${MyApplication.preferences.getString("memberNo")}")
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES, NdefMessage::class.java)

            rawMessages?.let { messages ->
                val uri = messages[0].records[0].toUri()
                nfcViewModel.fetchData(uri)
            }
        }
    }
}