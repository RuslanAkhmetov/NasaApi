package com.geekbrain.android.nasa_api.viewmodel

import com.geekbrain.android.nasa_api.repositoty.EPICResponse
import com.geekbrain.android.nasa_api.repositoty.PictureOfTheDayResponseData

sealed class AppStateEPIC{
    data class Success(val EPICResponseData: EPICResponse) :AppStateEPIC()
    data class Error(val error: Throwable): AppStateEPIC()
    object Loading: AppStateEPIC()
}
