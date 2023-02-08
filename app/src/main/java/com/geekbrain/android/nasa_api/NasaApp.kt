package com.geekbrain.android.nasa_api

import android.app.Application
import com.google.android.material.color.DynamicColors

class NasaApp : Application(){
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

}