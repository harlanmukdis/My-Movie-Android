package com.harlanmukdis.mymovie.ui.genre

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.utils.DataBindingAdapters
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ItemGenreBinding

class GenreAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listData = arrayListOf<Genre>()
    private var listenerCallback: GenreAdapterCallback? = null

    fun setOnClickListener(listener: GenreAdapterCallback) {
        this.listenerCallback = listener
    }

    fun setData(newListData: List<Genre>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun getData(): List<Genre> {
        return listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_genre,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = listData[position]
        val mHolder = holder as ListViewHolder
        mHolder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGenreBinding.bind(itemView)

        fun bind(data: Genre) {
            with(binding) {
                source = data

                val thumbAdapter = ThumbMovieAdapter()
                thumbAdapter.setData(data.movies)
                with(rvCoverMovie) {
                    layoutManager = LinearLayoutManager(
                        itemView.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    itemAnimator = DefaultItemAnimator()
                    adapter = thumbAdapter
                }
                thumbAdapter.setOnClickListener(object : ThumbMovieAdapter.MovieAdapterCallback {
                    override fun onThumbClick(data: Movie) {
                        listenerCallback?.onThumbClick(data)
                    }

                })

//                if (data.movies!!.isNotEmpty()) {
//                    shimmerThumbMovie.stopShimmer()
//                    shimmerThumbMovie.visibility = View.GONE
//                } else {
//                    shimmerThumbMovie.startShimmer()
//                    shimmerThumbMovie.visibility = View.VISIBLE
//                }

                tvViewAll.setOnClickListener {
                    listenerCallback?.onViewAllClick(data)
                }
            }
        }
    }

    interface GenreAdapterCallback {
        fun onViewAllClick(data: Genre)
        fun onThumbClick(data: Movie)
    }
}