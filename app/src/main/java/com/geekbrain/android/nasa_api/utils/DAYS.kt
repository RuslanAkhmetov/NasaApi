package com.geekbrain.android.nasa_api.utils

enum class DAYS(val offset: Int) {
    BEFORE_YESTERDAY(-2),
    YESTERDAY(-1),
    TODAY(0),
}