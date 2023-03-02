package com.geekbrain.android.nasa_api.recyclerview


data class Note (val id:Int = 0, var priority:Int = PRIOR_NORM, val name: String = "Note", var noteDescription : String? = "Description"){
    companion object {
        const val PRIOR_LOW = 0
        const val PRIOR_NORM = 1
        const val PRIOR_HIGH = 2
        const val PRIOR_HEADER = 4
    }

}

