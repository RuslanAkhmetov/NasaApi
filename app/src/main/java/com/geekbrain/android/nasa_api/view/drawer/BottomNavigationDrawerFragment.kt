package com.geekbrain.android.nasa_api.view.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrain.android.nasa_api.NasaApp.Companion.sharedPreferences
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.BottomNavigationLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    companion object {
        const val THEME = "activity_theme"
    }

    private val TAG ="BottomNavigationDrawerFragment"
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_theme_moon -> {
                    sharedPreferences.edit()?.putInt(THEME, R.style.MoonTheme)?.apply()
                }
                R.id.action_theme_mars -> {
                    sharedPreferences.edit()?.putInt(THEME, R.style.MarsTheme)?.apply()
                }
            }
            activity?.recreate()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}