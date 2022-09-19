package com.harlanmukdis.mymovie.ui.movie.detail.review.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ItemMovieBinding
import com.harlanmukdis.mymovie.databinding.ItemReviewBinding

class ReviewPageAdapter: PagingDataAdapter<Review, ReviewPageAdapter.ReviewPageViewHolder>(
    ReviewDiffCallBack()
) {

    var onItemClickListener: ((review: Review) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReviewPageViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ReviewPageViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

   inner class ReviewPageViewHolder(
        val binding: ItemReviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Review) {
            binding.apply {
                source = data

                content.apply {
                    setShowingLine(4)
                    setShowLessTextColor(Color.BLUE)
                    setShowMoreTextColor(Color.BLUE)
                    addShowLessText("Show less")
                    addShowMoreText("Show more")
                }

                val initials = data.author
                    .split(' ')
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .reduce { acc, s -> acc + s }
                initial.text = initials.substring(0, 1)

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${data.author_details.avatar_path}")
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            initial.visibility = View.VISIBLE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            initial.visibility = View.GONE
                            return false
                        }
                    })
                    .placeholder(R.drawable.bg_circle)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(avatar)

                root.setOnClickListener {
                    onItemClickListener?.invoke(data)
                }
            }
        }
    }
}