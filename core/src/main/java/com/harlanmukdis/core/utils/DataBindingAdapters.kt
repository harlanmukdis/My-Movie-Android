package com.harlanmukdis.core.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import java.text.SimpleDateFormat
import java.util.*

object DataBindingAdapters {
    val BASE_URL_IMG = "https://image.tmdb.org/t/p/original/"

    private val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(800)
        .setBaseAlpha(0.7f)
        .setHighlightAlpha(0.6f)
        .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
        .setAutoStart(true)
        .build()

    private val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }

    @BindingAdapter("android:imgRes")
    @JvmStatic
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @SuppressLint("ResourceType")
    @BindingAdapter("android:imgUrl")
    @JvmStatic
    fun setImageRemote(imageView: ImageView, url: String?) {
        val color = Color.parseColor("#d8d8d8")
        Glide.with(imageView.context)
            .load(BASE_URL_IMG + url)
            .placeholder(shimmerDrawable)
            .error(color)
            .into(imageView)
    }

    @BindingAdapter("visible")
    fun View.setVisible(show: Boolean) {
        visibility = if (show) VISIBLE else GONE
    }

    @BindingAdapter("android:dateFormat")
    @JvmStatic
    fun dateFormatter(view: TextView, oldDate: String?) {
        var newDate = ""
        if (oldDate != null && oldDate != "") {
            val oldFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = oldFormat.parse(oldDate)
            val newFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
            newDate = newFormat.format(date!!)
        }
        view.text = newDate
    }

    @BindingAdapter("bind:dateReviewFormat")
    fun dateReviewFormatter(view: TextView, oldDate: String?) {
        var newDate = ""
        if (oldDate != "") {
            val oldFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = oldFormat.parse(oldDate!!.substring(0, 10))
            val newFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            newDate = newFormat.format(date!!)
        }
        view.text = newDate
    }
}