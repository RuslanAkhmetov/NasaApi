package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.view.View
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EarthFragment: ViewBindingFragment<FragmentEarthBinding> (FragmentEarthBinding::inflate){

    var myTAG = "Earth"
        get() = field

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = ViewPager2AdapterForEarth(this)
        bindTabLayout()
    }

    private fun bindTabLayout() {
        TabLayoutMediator(
            binding.tablayout,
            binding.viewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        0 -> getString(R.string.today)
                        1 -> getString(R.string.yesterday)
                        2-> getString(R.string.before_yesterday)
                        else -> ""
                    }
                }
            }).attach()
    }


}