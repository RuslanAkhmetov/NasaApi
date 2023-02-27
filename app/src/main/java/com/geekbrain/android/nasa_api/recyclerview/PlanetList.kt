package com.geekbrain.android.nasa_api.recyclerview;

const val TYPE_EARTH = 0
const val TYPE_MARS = 1
const val TYPE_HEADER = 2
data class Planet ( val type: Int = TYPE_MARS, val name: String = "Text", val someDescription : String? = "Description"){

}

