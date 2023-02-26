package com.geekbrain.android.nasa_api.view.animation

import android.os.Bundle
import androidx.transition.TransitionManager
import android.view.animation.AnticipateOvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.ChangeBounds
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationStartBinding

class AnimationActivity : AppCompatActivity() {
    private val duration = 2000L
    private lateinit var binding: ActivityAnimationStartBinding

    private val TAG = "AnimationActivity"

    private var isFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimationStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val constraintSetStart = ConstraintSet()
        constraintSetStart.clone(binding.constraintContainer)
        val constraintSetEnd = ConstraintSet()
        constraintSetEnd.clone(this, R.layout.activity_animation_end)

        binding.tap.setOnClickListener{
            isFlag = !isFlag

            val transition = ChangeBounds()
            transition.interpolator = AnticipateOvershootInterpolator(1.0f)
            transition.duration = 1200
            TransitionManager.beginDelayedTransition(binding.constraintContainer,
                transition)

            constraintSetEnd.applyTo(binding.constraintContainer)
            if(isFlag){
                constraintSetStart.applyTo(binding.constraintContainer)
            } else{

            }
        }


        }

    }


