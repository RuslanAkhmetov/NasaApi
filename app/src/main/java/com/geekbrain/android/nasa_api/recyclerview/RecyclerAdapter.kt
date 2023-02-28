package com.geekbrain.android.nasa_api.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.RecyclerItemEarthBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemMarsBinding

class RecyclerAdapter(
    private var listPlanet: MutableList<Pair<Planet, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter{

    override fun getItemViewType(position: Int): Int {
        return listPlanet[position].first.type
    }

    fun setListPlanetRemove(newListPlanet: MutableList<Pair<Planet, Boolean>>, position: Int) {
        listPlanet = newListPlanet
        notifyItemRemoved(position)
    }

    fun setListPlanetAdd(newListPlanet: MutableList<Pair<Planet, Boolean>>, position: Int) {
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
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        abstract fun bind(planet: Pair<Planet, Boolean>)

        override fun onItemSelect() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.colorAccent))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class MarsViewHolder(val binding: RecyclerItemMarsBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        override fun bind(planet: Pair<Planet, Boolean>) {
            binding.name.text = planet.first.name

            binding.marsDescriptionTextView.visibility = if (listPlanet[layoutPosition].second){
                View.VISIBLE
            } else{
                View.GONE
            }

            binding.marsImageView.setOnClickListener{
                listPlanet[layoutPosition] = listPlanet[layoutPosition].let {
                    it.first to !it.second
                }

                notifyItemChanged(layoutPosition)
            }

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
        override fun bind(planet: Pair<Planet, Boolean>) {
            binding.name.text = planet.first.name
            binding.descriptionTextView.text = planet.first.someDescription
        }

    }

    class HeaderViewHolder(val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(planet: Pair<Planet, Boolean>) {
            binding.name.text = planet.first.name
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
       listPlanet.removeAt(fromPosition).apply {
           listPlanet.add(toPosition, this)
       }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }


}