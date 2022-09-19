package com.harlanmukdis.mymovie.ui.movie.list.adapter

import androidx.recyclerview.widget.DiffUtil
import com.harlanmukdis.core.domain.model.Movie

class MovieDiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}