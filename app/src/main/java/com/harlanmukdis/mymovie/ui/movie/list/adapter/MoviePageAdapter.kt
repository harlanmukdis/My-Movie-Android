package com.harlanmukdis.mymovie.ui.movie.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.mymovie.databinding.ItemMovieBinding

class MoviePageAdapter: PagingDataAdapter<Movie, MoviePageAdapter.MoviePageViewHolder>(
    MovieDiffCallBack()
) {

    var onItemClickListener: ((movie: Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MoviePageViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: MoviePageViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

   inner class MoviePageViewHolder(
        val binding: ItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            binding.apply {
                source = data

                root.setOnClickListener {
                    onItemClickListener?.invoke(data)
                }
            }
        }
    }
}