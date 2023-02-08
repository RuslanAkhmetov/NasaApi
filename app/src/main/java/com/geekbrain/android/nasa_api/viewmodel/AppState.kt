package com.geekbrain.android.nasa_api.viewmodel

import com.geekbrain.android.nasa_api.repositoty.PictureOfTheDayResponseData

sealed class AppState{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData) :AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
