package com.geekbrain.android.nasa_api.view.animation

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.transition.*
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    private val TAG = "AnimationActivity"

    private var isFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val titles: MutableList<String> = ArrayList()

        for(i in 0..4){
            titles.add("Item $i")
        }

        binding.button.setOnClickListener { image ->
            isFlag = !isFlag

            TransitionManager.beginDelayedTransition(binding.root)
            binding.transitionsContainer.removeAllViews()
            titles.shuffle()
            titles.forEach{
                binding.transitionsContainer.addView(TextView(this).apply {
                    text = it
                    textSize = 26f
                    ViewCompat.setTransitionName(this, it) //задали псевдонимы
                })
            }

           // binding.button.layoutParams = params
        }

    }


}