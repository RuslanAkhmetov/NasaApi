package com.geekbrain.android.nasa_api.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.databinding.RecyclerItemEarthBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemMarsBinding

class RecyclerAdapter(val listData: List<PlanetList>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return listData[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MarsViewHolder(binding: RecyclerItemMarsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    class EarthViewHolder(binding: RecyclerItemEarthBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    class HeaderViewHolder(binding: RecyclerItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}