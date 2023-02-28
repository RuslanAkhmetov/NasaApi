package com.geekbrain.android.nasa_api.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.databinding.RecyclerItemEarthBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemMarsBinding

class RecyclerAdapter(
    private var listPlanet: MutableList<Planet>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return listPlanet[position].type
    }

    fun setListPlanetRemove(newListPlanet: MutableList<Planet>, position: Int) {
        listPlanet = newListPlanet
        notifyItemRemoved(position)
    }

    fun setListPlanetAdd(newListPlanet: MutableList<Planet>, position: Int) {
        listPlanet = newListPlanet
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Planet.TYPE_EARTH-> {
                val binding = RecyclerItemEarthBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            Planet.TYPE_MARS -> {
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

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        abstract fun bind(planet: Planet)
    }

    inner class MarsViewHolder(val binding: RecyclerItemMarsBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Planet) {
            binding.name.text = planet.name
            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener{
                if (layoutPosition > 0 &&
                    getItemViewType(layoutPosition-1) != Planet.TYPE_HEADER) {
                    listPlanet.removeAt(layoutPosition).apply {
                        listPlanet.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }

            binding.moveItemDown.setOnClickListener{
                if(layoutPosition < listPlanet.size) {
                    listPlanet.removeAt(layoutPosition).apply {
                        listPlanet.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }
    }

    class EarthViewHolder(val binding: RecyclerItemEarthBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Planet) {
            binding.name.text = planet.name
            binding.descriptionTextView.text = planet.someDescription
        }
    }

    class HeaderViewHolder(val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Planet) {
            binding.name.text = planet.name
        }
    }
}