package com.geekbrain.android.nasa_api

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.android.material.color.DynamicColors

class NasaApp : Application(){

    companion object {
        lateinit var  sharedPreferences : SharedPreferences
    }
    override fun onCreate() {

        super.onCreate()
        sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(applicationContext)
        //DynamicColors.applyToActivitiesIfAvailable(this)
    }



}