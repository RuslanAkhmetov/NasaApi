package com.geekbrain.android.nasa_api.repositoty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val Earth_Polychromatic_Imaging_Camera = "EPIC/api/natural/images"

interface EPICAPI {


    @GET(Earth_Polychromatic_Imaging_Camera)
    fun getEPIC(@Query("api_key") apiKey: String): Call<EPICResponse>

}