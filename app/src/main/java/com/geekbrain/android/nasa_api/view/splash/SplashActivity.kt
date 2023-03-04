package com.geekbrain.android.nasa_api.view.splash

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.geekbrain.android.nasa_api.databinding.ActivitySplashBinding
import com.geekbrain.android.nasa_api.view.navigation.BottomBarActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private val delay = 4000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.imageView.animate().rotation(720f).setDuration(2000)

        ObjectAnimator.ofFloat(binding.imageView, View.ROTATION, 720f)
            .setDuration(2000L)
            .start()

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, BottomBarActivity::class.java))
            finish()
        }, delay)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
