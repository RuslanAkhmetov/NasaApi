package com.geekbrain.android.nasa_api.view.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.geekbrain.android.nasa_api.utils.DAYS
import com.geekbrain.android.nasa_api.view.picture.PictureOfTheDayFragment


@Suppress("DEPRECATION")
class ViewPager2AdapterForEarth(fragment: Fragment): FragmentStateAdapter(fragment){

    private val fragments= arrayOf( PictureOfTheDayFragment.newInstance(DAYS.TODAY),
                                    PictureOfTheDayFragment.newInstance(DAYS.YESTERDAY),
                                    PictureOfTheDayFragment.newInstance(DAYS.BEFORE_YESTERDAY))


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}