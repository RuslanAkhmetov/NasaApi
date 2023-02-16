package com.geekbrain.android.nasa_api.viewmodel

import com.geekbrain.android.nasa_api.repositoty.MarsCuriosityResponse

sealed class AppStateMarsCuriosity{
    data class Success(val marsCuriosityResponse: MarsCuriosityResponse) :AppStateMarsCuriosity()
    data class Error(val error: Throwable): AppStateMarsCuriosity()
    object Loading: AppStateMarsCuriosity()
}
