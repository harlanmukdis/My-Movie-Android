package com.harlanmukdis.mymovie.ui.movie.detail.about

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
import com.harlanmukdis.mymovie.R
import com.harlanmukdis.mymovie.databinding.ItemCastBinding
import java.util.*

class CastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listData = ArrayList<CastResponse.Cast>()

    fun setData(newListData: List<CastResponse.Cast>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    fun getData(): List<CastResponse.Cast> {
        return listData
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mHolder = holder as ListViewHolder
        val data = listData[position]
        mHolder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCastBinding.bind(itemView)
        fun bind(data: CastResponse.Cast) {
            with(binding) {
                source = data

                val initials = data.name
                    .split(' ')
                    .mapNotNull { it.firstOrNull()?.toString() }
                    .reduce { acc, s -> acc + s }
                initial.text = initials.substring(0, 1)

                Glide.with(itemView.context)
                    .load("https://image.tmdb.org/t/p/original${data.profile_path}")
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
                    .placeholder(R.drawable.bg_square)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(profile)
            }
        }
    }
}