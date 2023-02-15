package com.geekbrain.android.nasa_api

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.geekbrain.android.nasa_api.databinding.ActivityMainBinding
import com.geekbrain.android.nasa_api.view.drawer.BottomNavigationDrawerFragment
import com.geekbrain.android.nasa_api.view.picture.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {


    private var TAG = "MainActivity"

    companion object {
        val SHARED_PREF = "MySharedPref"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate: ${theme.resources.toString()}")
        //val sharedPref = PreferenceManager.getDefaultSharedPreferences(NasaApp.appContext)
        if (NasaApp.sharedPreferences.contains(BottomNavigationDrawerFragment.THEME)) {
            val theme = NasaApp.sharedPreferences.getInt(BottomNavigationDrawerFragment.THEME, -1)
            Log.i(TAG, "onCreate: ${BottomNavigationDrawerFragment.THEME}")
            Log.i(TAG, "onCreate: THEME = ${theme.toString()}")
            setTheme(theme)
        }

        Log.i(TAG, "onCreate: ${theme.resources.toString()}")

        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }

    }

}