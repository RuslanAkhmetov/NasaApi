package com.geekbrain.android.nasa_api.view.picture.utils

import java.text.SimpleDateFormat
import java.util.*

fun getSelectedDay(day: DAYS): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, day.offset)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    return dateFormat.format(calendar.time)
}

fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
    (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr)).
            findAll(this).map { it.range.first }.toList()