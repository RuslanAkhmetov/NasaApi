package com.geekbrain.android.nasa_api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrain.android.nasa_api.BuildConfig
import com.geekbrain.android.nasa_api.repositoty.MarsCuriosityResponse
import com.geekbrain.android.nasa_api.repositoty.RemoteRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MarsCuriocityViewModel(
    private val MarsCuriosityLiveData: MutableLiveData<AppStateMarsCuriosity> = MutableLiveData(),
    private val remoteRepository: RemoteRepositoryImpl = RemoteRepositoryImpl()
) : ViewModel() {

    private val TAG = "MarsCuriocityViewModel"

    fun getLiveData(): MutableLiveData<AppStateMarsCuriosity> {
        return MarsCuriosityLiveData
    }



    fun sendRequest() {
        MarsCuriosityLiveData.postValue(AppStateMarsCuriosity.Loading)
        remoteRepository.getMarsCuriosityAPI()
            .getPhotoFromMarsCuriosity(1000, BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }


    private val callback = object : Callback<MarsCuriosityResponse> {
        override fun onResponse(
            call: Call<MarsCuriosityResponse>,
            response: Response<MarsCuriosityResponse>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { MarsCuriosityLiveData
                    .postValue(AppStateMarsCuriosity.Success(it)) }
            } else {
                MarsCuriosityLiveData.postValue(AppStateMarsCuriosity
                    .Error(throw IllegalStateException("Something wrong")))
            }
        }

        override fun onFailure(call: Call<MarsCuriosityResponse>, t: Throwable) {
            MarsCuriosityLiveData.postValue(AppStateMarsCuriosity.Error(t))
        }

    }
}