package com.geekbrain.android.nasa_api.view.animation

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.*
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    private val TAG = "AnimationActivity"

    private var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener { image ->
            isFlag = !isFlag

            val transitionSet = TransitionSet()
            val changeImageTransform = ChangeImageTransform()
            val changeBounds = ChangeBounds()

            transitionSet.addTransition(changeBounds)
            transitionSet.addTransition(changeImageTransform)
            transitionSet.duration = 2000

            TransitionManager.beginDelayedTransition(binding.root, transitionSet)

            val params = image.layoutParams as LinearLayout.LayoutParams
            if (isFlag) {
                params.height = LinearLayout.LayoutParams.MATCH_PARENT
                (image as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                params.height = LinearLayout.LayoutParams.WRAP_CONTENT
                (image as ImageView).scaleType = ImageView.ScaleType.CENTER_INSIDE
            }
        }

    }


}