package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.ActivityBottomBarBinding
import com.geekbrain.android.nasa_api.view.layout.behaviors.LayoutFragment
import com.geekbrain.android.nasa_api.view.maket.MotionFragment
import com.google.android.material.badge.BadgeDrawable

class BottomBarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_view_earth -> {navigateTo(EarthFragment())}
                R.id.action_view_mars -> {navigateTo(MarsCuriosityFragment())}
                R.id.action_view_system -> {navigateTo(EPICFragment())}
                /*R.id.action_layout -> {navigateTo(LayoutFragment())}
                R.id.action_motion -> {navigateTo(MotionFragment())}*/
                else -> {}
            }
            true
        }

        binding.bottomNavigationView.selectedItemId = R.id.action_view_earth


        val badge =binding.bottomNavigationView.getOrCreateBadge(R.id.action_view_system)

        badge.number = 1000
        badge.maxCharacterCount = 5
        badge.badgeGravity = BadgeDrawable.BOTTOM_START

        binding.bottomNavigationView.removeBadge(R.id.action_view_system)

    }

    private fun navigateTo (fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }


}

