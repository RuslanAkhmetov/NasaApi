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
        Pair(Planet(id = 0, Planet.TYPE_HEADER, "Заголовок"), false),
        Pair(Planet(1, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(2, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(3, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(4, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(5, Planet.TYPE_EARTH, "Earth", "Blue Planet"), false),
        Pair(Planet(6, Planet.TYPE_MARS, "Mars", ""), false),
        Pair(Planet(7, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(8, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(9, Planet.TYPE_EARTH, "Earth"), false),
        Pair(Planet(10, Planet.TYPE_MARS, "Mars", null), false),
    )

    private var isNewList = false

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
        binding.recyclerActivityDiffUtilFAB.setOnClickListener{
            changeAdapterData()
        }

        return binding.root
    }

    val callbackAdd = object : AddItem {
        override fun add(position: Int) {
            planets.add(position, Pair(Planet(adapter.itemCount, Planet.TYPE_MARS, "Mars(New)"),false))
            adapter.setListPlanetAdd(planets, position)
        }
    }

    val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            planets.removeAt(position)
            adapter.setListPlanetRemove(planets, position)

        }
    }

    private fun changeAdapterData() {
        adapter.setListPlanetForDiffUtil (createItemList(isNewList).map { it }.toMutableList())
        isNewList=!isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Planet, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Planet(0, Planet.TYPE_HEADER, "Header"), false),
                Pair(Planet(1, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(2, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(3, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(4, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(5, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(6, Planet.TYPE_MARS, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Planet(0, Planet.TYPE_HEADER, "Header"), false),
                Pair(Planet(1, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(2, Planet.TYPE_MARS, "Jupiter", ""), false),
                Pair(Planet(3, Planet.TYPE_MARS, "Mars", ""), false),
                Pair(Planet(4, Planet.TYPE_MARS, "Neptune", ""), false),
                Pair(Planet(5, Planet.TYPE_MARS, "Saturn", ""), false),
                Pair(Planet(6, Planet.TYPE_MARS, "Mars", ""), false)
            )
        }
    }


}