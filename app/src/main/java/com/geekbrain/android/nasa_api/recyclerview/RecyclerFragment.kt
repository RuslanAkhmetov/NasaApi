package com.geekbrain.android.nasa_api.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.geekbrain.android.nasa_api.databinding.FragmentRecyclerBinding

class RecyclerFragment: Fragment() {
    private lateinit var binding:FragmentRecyclerBinding

    companion object {
        fun newInstance() = RecyclerFragment()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerBinding.inflate(inflater, container,false)
        val planets = arrayListOf(
            PlanetList( TYPE_HEADER,"Заголовок"),
            PlanetList(TYPE_EARTH, "Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_MARS,"Mars", ""),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_EARTH,"Earth"),
            PlanetList( TYPE_MARS,"Mars", null)
        )
        binding.recyclerView.adapter = RecyclerAdapter(planets)
        return binding.root
    }

}