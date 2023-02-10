package com.geekbrain.android.nasa_api.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrain.android.nasa_api.BuildConfig
import com.geekbrain.android.nasa_api.repositoty.PictureOfTheDayResponseData
import com.geekbrain.android.nasa_api.repositoty.RemoteRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val remoteRepository: RemoteRepositoryImpl = RemoteRepositoryImpl()
) : ViewModel() {

    fun getLiveData():MutableLiveData<AppState>{
        return liveData
    }

    fun sendRequest() {
        liveData.postValue(AppState.Loading)
        remoteRepository.getPictureOfTheDayApi()
            .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }

    fun sendRequest(selectedDate: String){
        liveData.postValue(AppState.Loading)
        remoteRepository.getPictureOfTheDayApi()
            .getPictureOfTheDayByDate(BuildConfig.NASA_API_KEY, selectedDate)
            .enqueue(callback)
    }

    private val callback = object : Callback<PictureOfTheDayResponseData> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { liveData.postValue(AppState.Success(it)) }
            } else {
                liveData.postValue(AppState.Error(throw IllegalStateException("Something wrong")))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            liveData.postValue(AppState.Error(t))
        }

    }
}