package com.github.chizoba.starwarz.ui.home.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.OnItemClickListener
import kotlinx.android.synthetic.main.character_item_layout.view.*


class CharacterRecyclerViewAdapter(private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, DiffCallback())

    override fun getItemCount() = mDiffer.currentList.size

    fun submitList(newList: List<Character>) {
        val list = mDiffer.currentList.toMutableList()
        list.addAll(newList)
        mDiffer.submitList(list)
    }

    fun resetList() {
        mDiffer.submitList(emptyList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = mDiffer.currentList[position]
        holder.bind(character, clickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Character, clickListener: OnItemClickListener) {
            itemView.nameView.text = model.name
            itemView.birthYearView.text = itemView.context.getString(R.string.birth_year, model.birthYear)
            itemView.setOnClickListener { clickListener.onItemClick(model.id) }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

}