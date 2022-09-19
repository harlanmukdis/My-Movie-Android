package com.harlanmukdis.mymovie.ui.genre

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ItemLoadMoreBinding
import com.harlanmukdis.mymovie.databinding.ItemThumbMovieBinding


class ThumbMovieAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val listData = arrayListOf<Movie>()
    private var listenerCallback: MovieAdapterCallback? = null
    companion object {
        val TYPE_LOAD_MORE = -1
        val TYPE_LIST = 0
    }

    fun setOnClickListener(listener: MovieAdapterCallback) {
        this.listenerCallback = listener
    }

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun getData(): List<Movie> {
        return listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LIST) {
            ListViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_thumb_movie,
                    parent,
                    false
                )
            )
        } else {
            LoadMoreViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_load_more,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == listData.size ||
                holder.itemViewType == TYPE_LOAD_MORE) {
            initLoadMore(holder, false)
        } else {
            initList(holder, position)
        }
    }

    override fun getItemCount(): Int = listData.size

    override fun getItemViewType(position: Int): Int {
        return if (position == listData.size) {
            TYPE_LOAD_MORE
        } else {
            TYPE_LIST
        }
    }

    override fun getItemId(position: Int): Long {
        return if (position == listData.size) {
            TYPE_LIST.toLong()
        } else {
            TYPE_LOAD_MORE.toLong()
        }
    }

    private fun initList(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as ListViewHolder
        val data = listData[position]
        mHolder.bind(data)
    }

    private fun initLoadMore(holder: RecyclerView.ViewHolder, isLoading: Boolean) {
        val mHolder = holder as LoadMoreViewHolder
    }

    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemThumbMovieBinding.bind(itemView)

        @SuppressLint("ResourceType")
        fun bind(data: Movie) {
            with(binding) {
                source = data

                cv.setOnClickListener {
                    listenerCallback?.onThumbClick(data)
                }
            }
        }
    }

    inner class LoadMoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val binding = ItemLoadMoreBinding.bind(itemView)

        fun bind() {
            with(binding) {

            }
        }
    }

    interface MovieAdapterCallback {
        fun onThumbClick(data: Movie)
    }
}