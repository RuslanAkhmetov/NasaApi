package com.geekbrain.android.nasa_api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import com.geekbrain.android.nasa_api.databinding.ActivityMainBinding
import com.geekbrain.android.nasa_api.view.drawer.BottomNavigationDrawerFragment
import com.geekbrain.android.nasa_api.view.picture.PictureOfTheDayFragment


class MainActivity : AppCompatActivity() {

    private var TAG = "MainActivity"


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        if (NasaApp.sharedPreferences.contains(BottomNavigationDrawerFragment.THEME)) {
            val theme = NasaApp.sharedPreferences.getInt(BottomNavigationDrawerFragment.THEME, -1)
            setTheme(theme)
        }


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