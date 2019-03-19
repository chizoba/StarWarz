package com.github.chizoba.starwarz.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.chizoba.starwarz.R
import com.github.chizoba.starwarz.ui.home.movies.Movie
import kotlinx.android.synthetic.main.character_movie_item_layout.view.*
import kotlinx.android.synthetic.main.movie_item_layout.view.*

const val HOME_MOVIES = "HOME_MOVIES"
const val CHARACTER_MOVIES = "CHARACTER_MOVIES"
const val HOME_MOVIES_TYPE = 1
const val CHARACTER_MOVIES_TYPE = 2

class MoviesRecyclerViewAdapter(private val viewType: String, private val clickListener: OnItemClickListener) :
    ListAdapter<Movie, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HOME_MOVIES_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
                MoviesViewHolder(view)
            }
            CHARACTER_MOVIES_TYPE -> {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.character_movie_item_layout, parent, false)
                CharacterMoviesViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item_layout, parent, false)
                MoviesViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            HOME_MOVIES_TYPE -> {
                (holder as MoviesViewHolder).bind(getItem(position), clickListener)
            }
            CHARACTER_MOVIES_TYPE -> {
                (holder as CharacterMoviesViewHolder).bind(getItem(position), clickListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (viewType) {
            HOME_MOVIES -> HOME_MOVIES_TYPE
            CHARACTER_MOVIES -> CHARACTER_MOVIES_TYPE
            else -> super.getItemViewType(position)
        }
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Movie, clickListener: OnItemClickListener) {
            itemView.movieTitleView.text = model.title
            itemView.releaseDateView.text = itemView.context.getString(R.string.release_date, model.releaseDate)
            itemView.directorView.text = itemView.context.getString(R.string.director, model.releaseDate)
            itemView.setOnClickListener { clickListener.onItemClick(model.id) }
        }
    }

    class CharacterMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: Movie, clickListener: OnItemClickListener) {
            itemView.titleView.text = model.title
            itemView.openingCrawlView.text = model.openingCrawl
            itemView.setOnClickListener { clickListener.onItemClick(model.id) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}