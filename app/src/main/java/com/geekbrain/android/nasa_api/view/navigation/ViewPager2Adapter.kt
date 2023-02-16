package com.geekbrain.android.nasa_api.view.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


@Suppress("DEPRECATION")
class ViewPager2Adapter(fa: FragmentActivity): FragmentStateAdapter(fa){

    private val fragments= arrayOf( MarsCuriosityFragment(), SystemFragment())


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}