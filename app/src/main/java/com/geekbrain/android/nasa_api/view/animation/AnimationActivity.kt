package com.geekbrain.android.nasa_api.view.animation

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.ActivityAnimationBinding

class AnimationActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAnimationBinding

    private val TAG = "AnimationActivity"

    private var isFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = Adapter()

    }



    inner class Adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.animation_explode_list_item,
                    parent,
                    false
                ) as View
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                val viewRect = Rect()
                it.getGlobalVisibleRect(viewRect)

                val explode = Explode()
                explode.duration = 1000
                explode.epicenterCallback = object : Transition.EpicenterCallback(){

                    override fun onGetEpicenter(transition: Transition): Rect {
                        return viewRect
                    }
                }
                TransitionManager.beginDelayedTransition(binding.transitionsContainer, explode)
                binding.recyclerView.adapter = null

            }
        }

        override fun getItemCount(): Int {
            return 32
        }

         inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

    }


}