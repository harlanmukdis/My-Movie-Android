package com.harlanmukdis.mymovie.ui.movie.detail.video

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.harlanmukdis.core.data.source.remote.response.CastResponse
import com.harlanmukdis.core.data.source.remote.response.VideoResponse
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ItemCastBinding
import com.harlanmukdis.mymovie.databinding.ItemVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.*

class VideoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listData = ArrayList<VideoResponse>()

    fun setData(newListData: List<VideoResponse>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun getData(): List<VideoResponse> {
        return listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as ListViewHolder
        val data = listData[position]
        mHolder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemVideoBinding.bind(itemView)
        fun bind(data: VideoResponse) {
            with(binding) {
                lifecycleOwner?.lifecycle?.addObserver(binding.youtubePlayer)

                youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        super.onReady(player)
                        player.cueVideo(data.key, 0F)
                    }
                })
            }
        }
    }
}