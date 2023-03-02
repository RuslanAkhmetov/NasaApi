package com.geekbrain.android.nasa_api.recyclerview.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.geekbrain.android.nasa_api.recyclerview.Note

class DiffUtilCallback(
    private val oldItems: List<Pair<Note,Boolean>>,
    private val newItems: List<Pair<Note,Boolean>>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size

    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.name == newItems[newItemPosition].first.name
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]

        return Change(old, new)
    }
}

data class Change<T>(val oldData: T, val newData: T)

fun <T> createCombinePayload(payloads: List<Change<T>>) : Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return Change(firstChange.oldData, lastChange.oldData)
}