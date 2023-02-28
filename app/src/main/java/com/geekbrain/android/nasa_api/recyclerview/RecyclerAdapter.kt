package com.geekbrain.android.nasa_api.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.RecyclerItemEarthBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemMarsBinding
import com.geekbrain.android.nasa_api.recyclerview.diffutil.Change
import com.geekbrain.android.nasa_api.recyclerview.diffutil.DiffUtilCallback
import com.geekbrain.android.nasa_api.recyclerview.diffutil.createCombinePayload

class RecyclerAdapter(
    private var listPlanet: MutableList<Pair<Planet, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter{

    private val TAG = "RecyclerAdapter"

    override fun getItemViewType(position: Int): Int {
        return listPlanet[position].first.type
    }

    fun setListPlanetForDiffUtil(newListPlanet: MutableList<Pair<Planet, Boolean>>) {
        val diffAccount = DiffUtil.calculateDiff(DiffUtilCallback(listPlanet, newListPlanet))
        diffAccount.dispatchUpdatesTo(this)
        listPlanet = newListPlanet
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

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinePayloads = createCombinePayload(payloads as List<Change<Pair<Planet, Boolean>>>)
            Log.i(TAG, "onBindViewHolder new: ${createCombinePayloads.newData.first.name}")
            Log.i(TAG, "onBindViewHolder old: ${createCombinePayloads.oldData.first.name}")
            Log.i(TAG, "onBindViewHolder isThe same: ${{createCombinePayloads.oldData.first.name.toString()} == {createCombinePayloads.newData.first.name.toString()}}")
            //if (createCombinePayloads.newData.first.name != createCombinePayloads.oldData.first.name)   // в данном случае это лишнее
                holder.itemView.findViewById<TextView>(R.id.name).text =
                    createCombinePayloads.newData.first.name
            //}
        }
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