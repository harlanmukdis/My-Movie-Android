package com.harlanmukdis.mymovie.ui.movie.detail.review.adapter

import androidx.recyclerview.widget.DiffUtil
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review

class ReviewDiffCallBack : DiffUtil.ItemCallback<Review>() {
    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }
}