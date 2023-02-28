package com.geekbrain.android.nasa_api.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.databinding.RecyclerItemEarthBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemMarsBinding

class RecyclerAdapter(val listPlanet: List<Planet>) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return listPlanet[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = RecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding = RecyclerItemMarsBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding = RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listPlanet[position])


    }

    override fun getItemCount(): Int {
        return listPlanet.size
    }

    abstract class BaseViewHolder(view: View):
            RecyclerView.ViewHolder(view){
                abstract fun bind(data:Planet)
            }

    class MarsViewHolder(val binding: RecyclerItemMarsBinding) : BaseViewHolder(binding.root) {
        override fun bind(planet: Planet){
            binding.name.text = planet.name

        }
    }

    class EarthViewHolder(val binding: RecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Planet){
            binding.name.text = planet.name
            binding.descriptionTextView.text = planet.someDescription
        }
    }

    class HeaderViewHolder(val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Planet){
            binding.name.text = planet.name
        }
    }
}