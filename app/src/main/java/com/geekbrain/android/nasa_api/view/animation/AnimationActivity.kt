package com.geekbrain.android.nasa_api.view.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.transition.*
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private val duration = 2000L
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

        binding.fab.setOnClickListener { image ->
            isFlag = !isFlag

            if(isFlag){
                ObjectAnimator
                    .ofFloat(binding.plusImageview, View.ROTATION, 0f, 675f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator
                    .ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -200f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator
                    .ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -300f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.5f)
                    .setDuration(duration)
                    .start()


                binding.optionOneContainer.animate().alpha(1f)
                    .setDuration(duration)
                    .setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOneContainer.isClickable = true
                            binding.optionOneContainer.setOnClickListener{
                                Toast.makeText(this@AnimationActivity, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                )

                binding.optionTwoContainer.animate().alpha(1f)
                    .setDuration(duration)
                    .setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwoContainer.isClickable = true
                            binding.optionTwoContainer.setOnClickListener{
                                Toast.makeText(this@AnimationActivity, "Option 1", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                )

            } else {
                ObjectAnimator
                    .ofFloat(binding.plusImageview, View.ROTATION,  675f, 0f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator
                    .ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator
                    .ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, 0f)
                    .setDuration(duration)
                    .start()
                ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f)
                    .setDuration(duration)
                    .start()


                binding.optionOneContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionOneContainer.isClickable = false

                        }
                    }
                )

                binding.optionTwoContainer.animate().alpha(0f).setDuration(duration).setListener(
                    object : AnimatorListenerAdapter(){
                        override fun onAnimationEnd(animation: Animator) {
                            binding.optionTwoContainer.isClickable = false

                        }
                    }
                )

            }

        }

    }


}