package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EarthFragment: SpaceFragment() {

    private var _binding: FragmentEarthBinding?  = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.myTag = "Earth1"
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}