package com.geekbrain.android.nasa_api.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.geekbrain.android.nasa_api.databinding.FragmentRecyclerBinding

class RecyclerFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerBinding

    private val notes = arrayListOf(
        Pair(Note(id = 0, Note.PRIOR_HEADER, "To Do"), false),
        Pair(Note(1, Note.PRIOR_NORM, "Feed", "Feed Dog"), false),
        Pair(Note(2, Note.PRIOR_LOW, "Feed", "Feed Cat"), false),
        Pair(Note(3, Note.PRIOR_LOW, "Feed", "Feed Fish"), false),
        Pair(Note(id = 0, Note.PRIOR_HEADER, "Notes"), false),
        Pair(Note(7, Note.PRIOR_LOW, "Weather", "Today is Shiny day"), false),
        Pair(Note(8, Note.PRIOR_LOW, "Traffic", "Traffic jam"), false),
    )


    lateinit var adapter: RecyclerAdapter

    companion object {
        fun newInstance() = RecyclerFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerBinding.inflate(inflater, container, false)
        adapter = RecyclerAdapter(notes, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(binding.recyclerView)

        binding.recyclerActivityFAB.setOnClickListener{
            callbackAdd.add(notes.size)
        }

        return binding.root
    }

    val callbackAdd = object : AddItem {
        override fun add(position: Int) {
            notes.add(position, Pair(Note(adapter.itemCount, Note.PRIOR_NORM, "TO DO(New)"),false))
            adapter.setListNoteAdd(notes, position)
        }
    }

    val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            notes.removeAt(position)
            adapter.setListNoteRemove(notes, position)

        }
    }


}