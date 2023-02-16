package com.geekbrain.android.nasa_api.repositoty


import com.google.gson.annotations.SerializedName

data class EPICItem(
    val caption: String,
    val date: String,
    val identifier: String,
    val image: String,
    val version: String
)