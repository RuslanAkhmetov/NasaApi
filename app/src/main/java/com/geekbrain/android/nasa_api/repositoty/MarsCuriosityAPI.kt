package com.geekbrain.android.nasa_api.repositoty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val MARS_CURIOSITY_PATH = "mars-photos/api/v1/rovers/curiosity/photos"

interface MarsCuriosityAPI {


    @GET(MARS_CURIOSITY_PATH)
    fun getPhotoFromMarsCuriosity(@Query("sol") sol : Int, @Query("api_key") apiKey: String): Call<MarsCuriosityResponse>

}