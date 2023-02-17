package com.geekbrain.android.nasa_api.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrain.android.nasa_api.BuildConfig
import com.geekbrain.android.nasa_api.repositoty.EPICResponse
import com.geekbrain.android.nasa_api.repositoty.RemoteRepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val baseURL = "https://epic.gsfc.nasa.gov/archive/natural/"

class EPICViewModel(
    private val EPICliveData: MutableLiveData<AppStateEPIC> = MutableLiveData(),
    private val remoteRepository: RemoteRepositoryImpl = RemoteRepositoryImpl()
) : ViewModel() {

    private val TAG = "EPICViewModel"

    fun getLiveData(): MutableLiveData<AppStateEPIC> {
        return EPICliveData
    }

    fun generateImageURL(response: AppStateEPIC): String? {
        var url = ""
        if (response is AppStateEPIC.Success) {
            val epic = response.EPICResponseData[0]
            val date = epic.date.subSequence(0,10).toString().replace('-', '/')
            val image = epic.image

            val format = "jpg"
            url = String.format("%s%s/%s/%s.%s", baseURL, date, format, image, format )

            Log.i(TAG, "generateImageURL: $url")
        } else{
            return null
        }
        return url

    }

    fun sendRequest() {
        EPICliveData.postValue(AppStateEPIC.Loading)
        remoteRepository.getEPICAPI()
            .getEPIC(BuildConfig.NASA_API_KEY)
            .enqueue(callback)
    }


    private val callback = object : Callback<EPICResponse> {
        override fun onResponse(
            call: Call<EPICResponse>,
            response: Response<EPICResponse>
        ) {
            if (response.isSuccessful) {
                response.body()?.let { EPICliveData.postValue(AppStateEPIC.Success(it)) }
            } else {
                EPICliveData.postValue(AppStateEPIC
                    .Error(throw IllegalStateException("Something wrong")))
            }
        }

        override fun onFailure(call: Call<EPICResponse>, t: Throwable) {
            EPICliveData.postValue(AppStateEPIC.Error(t))
        }

    }
}