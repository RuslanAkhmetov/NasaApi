package com.geekbrain.android.nasa_api.recyclerview

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geekbrain.android.nasa_api.R
import com.geekbrain.android.nasa_api.databinding.RecyclerItemHeaderBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemNoteLowPriorityBinding
import com.geekbrain.android.nasa_api.databinding.RecyclerItemUsualPriorityBinding
import com.geekbrain.android.nasa_api.recyclerview.diffutil.Change
import com.geekbrain.android.nasa_api.recyclerview.diffutil.DiffUtilCallback
import com.geekbrain.android.nasa_api.recyclerview.diffutil.createCombinePayload

class RecyclerAdapter(
    private var listNote: MutableList<Pair<Note, Boolean>>,
    val callbackAdd: AddItem,
    val callbackRemove: RemoveItem
) :
    RecyclerView.Adapter<RecyclerAdapter.BaseViewHolder>(), ItemTouchHelperAdapter{

    private val TAG = "RecyclerAdapter"

    override fun getItemViewType(position: Int): Int {
        return listNote[position].first.priority
    }

    fun setListNoteForDiffUtil(newListPlanet: MutableList<Pair<Note, Boolean>>) {
        val diffAccount = DiffUtil.calculateDiff(DiffUtilCallback(listNote, newListPlanet))
        diffAccount.dispatchUpdatesTo(this)
        listNote = newListPlanet
    }

    fun setListNoteRemove(newListPlanet: MutableList<Pair<Note, Boolean>>, position: Int) {
        listNote = newListPlanet
        notifyItemRemoved(position)
    }

    fun setListNoteAdd(newListPlanet: MutableList<Pair<Note, Boolean>>, position: Int) {
        listNote = newListPlanet
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Note.PRIOR_LOW-> {
                val binding = RecyclerItemNoteLowPriorityBinding.inflate(LayoutInflater.from(parent.context))
                LowPriorityViewHolder(binding)
            }
            Note.PRIOR_NORM -> {
                val binding = RecyclerItemUsualPriorityBinding.inflate(LayoutInflater.from(parent.context))
                UsualPriorityViewHolder(binding)
            }
            Note.PRIOR_HEADER->{
                val binding = RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = RecyclerItemHeaderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinePayloads = createCombinePayload(payloads as List<Change<Pair<Note, Boolean>>>)
                holder.itemView.findViewById<TextView>(R.id.name).text =
                    createCombinePayloads.newData.first.name
        }
    }

    override fun getItemCount(): Int {
        return listNote.size
    }

    abstract class BaseViewHolder(view: View) :
        RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        abstract fun bind(planet: Pair<Note, Boolean>)

        override fun onItemSelect() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(itemView.context, R.color.colorAccent))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class UsualPriorityViewHolder(val binding: RecyclerItemUsualPriorityBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun bind(noteItem: Pair<Note, Boolean>) {
            binding.name.text = noteItem.first.name
            binding.descriptionTextView.text =
                Editable.Factory.getInstance().newEditable(noteItem.first.noteDescription)

            binding.editItemImageView.setOnClickListener {
                listNote[layoutPosition] = listNote[layoutPosition].let {
                    it.first to !it.second
                }
                if (listNote[layoutPosition].second) {
                    binding.descriptionTextView.focusable = View.FOCUSABLE
                } else{
                    binding.descriptionTextView.focusable = View.NOT_FOCUSABLE
                    notifyItemChanged(layoutPosition)
                }
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    listNote[layoutPosition] = listNote[layoutPosition].let {
                        it.first.noteDescription = binding.descriptionTextView.text.toString()
                        it.first to !it.second
                    }

                }
            }

            binding.descriptionTextView.addTextChangedListener(textWatcher)


            binding.moveItemDown.setOnClickListener{
                noteItem.first.priority = Note.PRIOR_LOW
                notifyItemChanged(layoutPosition)
            }


        }

    }

    inner class LowPriorityViewHolder(val binding: RecyclerItemNoteLowPriorityBinding) :
        BaseViewHolder(binding.root), ItemTouchHelperViewHolder {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun bind(noteItem: Pair<Note, Boolean>) {
            binding.name.text = noteItem.first.name
            binding.descriptionTextView.text =
                Editable.Factory.getInstance().newEditable(noteItem.first.noteDescription)

            binding.editItemImageView.setOnClickListener {
                listNote[layoutPosition] = listNote[layoutPosition].let {
                    it.first to !it.second
                }
                if (listNote[layoutPosition].second) {
                    binding.descriptionTextView.focusable = View.FOCUSABLE
                } else{
                    binding.descriptionTextView.focusable = View.NOT_FOCUSABLE
                    notifyItemChanged(layoutPosition)
                }
            }

            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    listNote[layoutPosition] = listNote[layoutPosition].let {
                        it.first.noteDescription = binding.descriptionTextView.text.toString()
                        it.first to !it.second
                    }

                }
            }

            binding.descriptionTextView.addTextChangedListener(textWatcher)

            binding.moveItemUp.setOnClickListener{
                    noteItem.first.priority = Note.PRIOR_NORM
                    notifyItemChanged(layoutPosition)
            }


        }

    }

    class HeaderViewHolder(val binding: RecyclerItemHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun bind(noteItem: Pair<Note, Boolean>) {
            binding.name.text = noteItem.first.name
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
       listNote.removeAt(fromPosition).apply {
           listNote.add(toPosition, this)
       }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }


}

