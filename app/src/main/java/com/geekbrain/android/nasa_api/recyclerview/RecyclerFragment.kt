package com.geekbrain.android.nasa_api.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrain.android.nasa_api.databinding.FragmentRecyclerBinding

class RecyclerFragment: Fragment() {
    private lateinit var binding:FragmentRecyclerBinding

    private val planets = arrayListOf(
        Planet( TYPE_HEADER,"Заголовок"),
        Planet(TYPE_EARTH, "Earth"),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_EARTH,"Earth", "Blue Planet"),
        Planet( TYPE_MARS,"Mars", ""),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_EARTH,"Earth"),
        Planet( TYPE_MARS,"Mars", null)
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
        binding = FragmentRecyclerBinding.inflate(inflater, container,false)
        adapter = RecyclerAdapter(planets, callbackAdd, callbackRemove )
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    val callbackAdd = object : AddItem{
        override fun add(position: Int) {
            planets.add(position, Planet(TYPE_MARS, "Mars(New)"))
            adapter.setListPlanetAdd(planets, position)
        }
    }

    val callbackRemove = object : RemoveItem{
        override fun remove(position: Int) {
            planets.removeAt(position)
            adapter.setListPlanetRemove(planets, position)

        }

    }


}