package com.hyundai.hpass.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hyundai.hpass.Websocket.WebSocketClient
import com.hyundai.hpass.Websocket.WebSocketConnectionState
import com.hyundai.hpass.databinding.NfcActivityMainBinding
import com.hyundai.hpass.main.MainViewModel
import com.hyundai.hpass.socialLogIn.MyApplication
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime


class NfcMainActivity : AppCompatActivity() {

    private lateinit var binding: NfcActivityMainBinding
    private lateinit var nfcViewModel: NfcViewModel
    private lateinit var mainViewModel: MainViewModel
    private val webSocketClient = WebSocketClient("wss://hpass.shop/socket/NfcCall")

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
            if (nfcViewModel.getIsSuccessVisitFiveStore().value == true) {
                binding.confirmButton.visibility = View.INVISIBLE
                binding.lottieAlert.visibility = View.INVISIBLE
                binding.lottieCheck.visibility = View.INVISIBLE

                binding.issueButton.visibility = View.VISIBLE
                binding.lottieFiveStart.visibility = View.VISIBLE
                binding.infoText.text = "오늘의 매장을 모두 방문하셨습니다"
                binding.infoSubtext.text = "추가 쿠폰을 받으세요!"
            } else {
                finish()
            }
        }

        binding.issueButton.setOnClickListener {
            nfcViewModel.issuePromotionCoupon()
        }

        binding.finishButton.setOnClickListener {
            finish()
        }
    }

    private fun bind() {
        mainViewModel.getLoginPass().observe(this) { pass ->
            if (pass == false) {
                binding.lottieFail.visibility = View.VISIBLE
                binding.infoText.text = "로그인이 필요합니다"
                binding.infoSubtext.text = "로그인해주세요!"
            }
        }

        mainViewModel.getIsSubscribed().observe(this) { isSubscribed ->
            if (isSubscribed == false) {
                binding.lottieFail.visibility = View.VISIBLE
                binding.infoText.text = "HPass 구독자가 아닙니다"
                binding.infoSubtext.text = "구독자만 이용할 수 있는 서비스입니다"
            } else {
                handleIntent(intent)
            }
        }

        nfcViewModel.getPopUpStore().observe(this) { popUpStore ->
            if (popUpStore == null) {
                binding.lottieFail.visibility = View.VISIBLE
                binding.infoText.text = "팝업스토어 예약 내역이 없습니다"
                binding.infoSubtext.text = "팝업스토어 예약 먼저 해주세요!"
            } else {

                Glide
                    .with(this)
                    .load(popUpStore.popUpImage)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.image)
                binding.storeName.text = popUpStore.popUpName
                binding.bookingTime.text = popUpStore.bookingDate + " " + popUpStore.bookingTime

                popUpStore.bookingTime.let {
                    val time = it.split(" : ")
                    val hour = time[0].toInt()

                    val zoneDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

                    if (zoneDateTime.hour >= hour + 1) {
                        binding.lottieFail.visibility = View.VISIBLE
                        binding.infoText.text = "입장 가능한 시간이 아닙니다"
                        binding.infoSubtext.text = "예약시간이 지났습니다!"
                    } else if (zoneDateTime.hour < hour - 1) {
                        binding.lottieAlert.visibility = View.VISIBLE
                        binding.infoText.text = "입장가능한 시간이 아닙니다"
                        binding.infoSubtext.text = "조금만 더 기다려주세요!"
                    } else {
                        binding.cardViewLayout.visibility = View.VISIBLE
                        binding.infoText.text = "예약 내역이 확인되었습니다"
                        binding.infoSubtext.text = "지금 바로 입장해주세요!"
                    }
                }
            }
        }

        nfcViewModel.getVisitStore().observe(this) { visitStore ->
            if (visitStore.storeFloor == "already") {
                binding.lottieAlert.visibility = View.VISIBLE
                binding.infoText.text = "이미 방문한 매장입니다"
                binding.infoSubtext.text = "다른 매장을 방문해주세요!"
            } else if (visitStore.storeFloor == "not today") {
                binding.lottieFail.visibility = View.VISIBLE
                binding.infoText.text = "오늘의 매장이 아닙니다"
                binding.infoSubtext.text = "오늘의 매장을 확인해주세요!"
            } else if (visitStore.visitStatus) {
                binding.lottieCheck.visibility = View.VISIBLE
                binding.infoText.text = "오늘의 매장 방문 완료"
                binding.infoSubtext.text = "매장 쿠폰이 발급되었습니다!"
            }
        }

        nfcViewModel.getCoupon().observe(this) { coupon ->
            if (coupon == "false") {
                binding.lottieAlert.visibility = View.VISIBLE
                binding.infoText.text = "이미 받은 쿠폰입니다"
                binding.infoSubtext.text = "쿠폰함을 확인해주세요!"
            } else {
                binding.lottieCheck.visibility = View.VISIBLE
                binding.infoText.text = "쿠폰이 발급되었습니다"
                binding.infoSubtext.text = "쿠폰함을 확인해주세요!"
            }
        }

        nfcViewModel.getIsHPass().observe(this) { isHPass ->
            if (isHPass == true) {
                binding.lottieCheck.visibility = View.VISIBLE
                binding.infoText.text = "구독 정보가 확인되었습니다"
                binding.infoSubtext.text = "할인된 가격으로 즐기세요!"

                lifecycleScope.launch {

                    var count = 0

                    while (webSocketClient.connectionState == WebSocketConnectionState.DISCONNECTED || count == 25) {
                        delay(100)
                        count += 1
                    }

                    if (webSocketClient.connectionState == WebSocketConnectionState.CONNECTED) {
                        webSocketClient.sendMessage("member::${MyApplication.preferences.getString("memberNo")}")
                    } else {
                        Toast.makeText(this@NfcMainActivity, "다시 시도해주세요!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        nfcViewModel.getPromotionCoupon().observe(this) { promotionCoupon ->
            binding.lottieFiveStart.visibility = View.INVISIBLE
            binding.issueButton.visibility = View.INVISIBLE
            binding.finishButton.visibility = View.VISIBLE

            if (promotionCoupon == "false") {
                binding.lottieAlert.visibility = View.VISIBLE
                binding.infoText.text = "이미 받은 쿠폰입니다"
                binding.infoSubtext.text = "쿠폰함을 확인해주세요!"
            } else {
                binding.lottieCheck.visibility = View.VISIBLE
                binding.infoText.text = "쿠폰이 발급되었습니다"
                binding.infoSubtext.text = "쿠폰함을 확인해주세요!"
            }
        }
    }

    private fun handleIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
            val rawMessages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES,
                NdefMessage::class.java
            )

            rawMessages?.let { messages ->
                val uri = messages[0].records[0].toUri()
                nfcViewModel.fetchData(uri)
            }
        }
    }
}