package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrain.android.nasa_api.databinding.ActivityViewPagerBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerActivity: AppCompatActivity() {
    private lateinit var binding: ActivityViewPagerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPager2Adapter(this)

        bindTabLayout()

        }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tablayout,
            binding.viewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        0 -> "Earth"
                        1 -> "Mars"
                        2 -> "Sun System"
                        else -> ""
                    }
                }
            }).attach()
    }
}

