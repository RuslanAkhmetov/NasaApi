package com.geekbrain.android.nasa_api.repositoty

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepositoryImpl : RemoteRepository {

    private val baseURL = "https://api.nasa.gov/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    fun getPictureOfTheDayApi(): PictureOfTheDayAPI {

        return retrofit.create(PictureOfTheDayAPI::class.java)
    }
}