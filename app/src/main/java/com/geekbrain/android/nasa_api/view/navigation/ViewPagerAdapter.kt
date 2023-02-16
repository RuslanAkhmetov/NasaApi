package com.geekbrain.android.nasa_api.view.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter



@Suppress("DEPRECATION")
class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager){

    private val fragments= arrayOf(EarthFragment(), MarsFragment(), SystemFragment())

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].myTag
        //return fragments[position].tag.toString()

    }
}