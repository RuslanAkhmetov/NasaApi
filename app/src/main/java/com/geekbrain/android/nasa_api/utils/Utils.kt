package com.geekbrain.android.nasa_api.utils

import java.text.SimpleDateFormat
import java.util.*

fun getSelectedDay(day: DAYS): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, day.offset)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(calendar.time)
}