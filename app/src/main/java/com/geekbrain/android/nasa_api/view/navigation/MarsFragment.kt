package com.geekbrain.android.nasa_api.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrain.android.nasa_api.R

class MarsFragment: SpaceFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.myTag = "Mars"
        return inflater.inflate(R.layout.fragment_mars, container, false)

    }
}