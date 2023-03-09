package com.geekbrain.android.nasa_api.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.geekbrain.android.nasa_api.databinding.ActivitySplashBinding
import com.geekbrain.android.nasa_api.view.navigation.BottomBarActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.imageView.animate().rotation(720f).setDuration(2000)

        //ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 720f)
        //    .setDuration(2000L)
        //    .start()

        val millSec = 2000F
        val timer = object : CountDownTimer(millSec.toLong(), 100) {
            override fun onTick(millisUntilFinished: Long) {
                binding.progressBar.progress = ((1 - millisUntilFinished / millSec) * 100).toInt()
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, BottomBarActivity::class.java))
            }

        }

        timer.start()

    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
