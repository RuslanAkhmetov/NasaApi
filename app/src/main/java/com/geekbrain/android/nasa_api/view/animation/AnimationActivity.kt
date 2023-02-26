package com.geekbrain.android.nasa_api.view.animation

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    private val TAG = "AnimationActivity"

    private var isFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { image ->
            isFlag = !isFlag

            val changeBounds = ChangeBounds()
            changeBounds.duration = 2000L
            changeBounds.setPathMotion(ArcMotion())

            TransitionManager.beginDelayedTransition(binding.root, changeBounds)
            val params = image.layoutParams as FrameLayout.LayoutParams
            if (isFlag) {
                params.gravity = Gravity.TOP or Gravity.START

            } else {
                params.gravity = Gravity.BOTTOM or Gravity.END

            }
            binding.button.layoutParams = params
        }

    }


}