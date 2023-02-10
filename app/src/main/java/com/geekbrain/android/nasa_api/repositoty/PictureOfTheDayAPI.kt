package com.geekbrain.android.nasa_api.repositoty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val PICTURE_OF_THE_DAY = "planetary/apod"

interface PictureOfTheDayAPI {


    @GET(PICTURE_OF_THE_DAY)
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PictureOfTheDayResponseData>

    @GET(PICTURE_OF_THE_DAY)
    fun getPictureOfTheDayByDate(
        @Query("api_key") apiKey: String,
        @Query("date") date: String            //date in format "YYYY-MM-DD"
    ): Call<PictureOfTheDayResponseData>
}