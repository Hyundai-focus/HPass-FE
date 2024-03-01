package com.hyundai.hpass.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hyundai.hpass.R
import com.hyundai.hpass.databinding.ActivitySplashBinding
import com.hyundai.hpass.socialLogIn.MyApplication

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: MainViewModel
    private var isChecked = false
    private var splashDone = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        configureEvent()
        bind()

        Handler(Looper.getMainLooper()).postDelayed({
            splashDone = true
            //3초 후에 verify 완료.
            if (isChecked) goToMain()
        }, 3000)
    }
    private fun configureEvent() {
        Glide.with(this)
            .asGif()
            .load(R.raw.splash)
            .listener(object : RequestListener<GifDrawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<GifDrawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
                override fun onResourceReady(
                    resource: GifDrawable,
                    model: Any,
                    target: Target<GifDrawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.setLoopCount(1) // GIF를 한 번만 재생하도록 설정
                    return false
                }
            })
            .into(binding.splashGif)
    }
    private fun bind() {
        viewModel.getLoginPass().observe(this) { pass ->
            MyApplication.preferences.setString("loginPass", pass.toString())
            Log.d("MainActivity: 로그인 여부", MyApplication.preferences.getString("loginPass"))
            isChecked = true
            if (splashDone)  goToMain()
        }
    }

    private fun goToMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}