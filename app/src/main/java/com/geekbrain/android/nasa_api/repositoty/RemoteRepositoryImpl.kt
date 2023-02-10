package com.geekbrain.android.nasa_api.repositoty

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepositoryImpl : RemoteRepository {

    private val baseURL = "https://api.nasa.gov/"
    private val logging = HttpLoggingInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(client)
        .build()


    fun getPictureOfTheDayApi(): PictureOfTheDayAPI {
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }
}