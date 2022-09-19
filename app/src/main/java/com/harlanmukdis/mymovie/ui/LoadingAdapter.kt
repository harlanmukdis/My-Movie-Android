package com.harlanmukdis.mymovie.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harlanmukdis.mymovie.databinding.ItemLoadMoreBinding

class LoadingAdapter : LoadStateAdapter<LoadingAdapter.LoadingViewHolder>() {
    private var isLoading: Boolean = false

    fun setLoading(state: Boolean) {
        this.isLoading = state
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    override fun onBindViewHolder(holder: LoadingViewHolder, loadState: LoadState) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingViewHolder(
            ItemLoadMoreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    class LoadingViewHolder(
        private val binding: ItemLoadMoreBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            with(binding) {
                /*if (isLoading) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }*/
            }
        }
    }
}