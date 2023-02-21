package com.geekbrain.android.nasa_api.view.animation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    private val TAG = "AnimationActivity"

    private var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: ")

        binding.button.setOnClickListener {
            val myAutoTransition  = TransitionSet()
            myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
            val slide = Slide()
            slide.duration = 1000
            val changeBounds = ChangeBounds()
            changeBounds.duration = 1000
            myAutoTransition.addTransition(slide)
            myAutoTransition.addTransition(changeBounds)
            TransitionManager.beginDelayedTransition(binding.transitionsContainer, myAutoTransition)
            isFlag = !isFlag

            binding.text.visibility = if(isFlag) View.VISIBLE else View.GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}