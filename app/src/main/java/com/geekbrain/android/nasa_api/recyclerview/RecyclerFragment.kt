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

    private val planets = arrayListOf(
        Pair(Planet(Planet.TYPE_HEADER, "Заголовок"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth", "Blue Planet"), false),
        Pair(Planet(Planet.TYPE_MARS, "Mars", ""), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(Planet.TYPE_MARS, "Mars", null), false),
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
        adapter = RecyclerAdapter(planets, callbackAdd, callbackRemove)
        binding.recyclerView.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(binding.recyclerView)
        return binding.root
    }

    val callbackAdd = object : AddItem {
        override fun add(position: Int) {
            planets.add(position, Pair(Planet(Planet.TYPE_MARS, "Mars(New)"),false))
            adapter.setListPlanetAdd(planets, position)
        }
    }

    val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            planets.removeAt(position)
            adapter.setListPlanetRemove(planets, position)

        }
    }


}