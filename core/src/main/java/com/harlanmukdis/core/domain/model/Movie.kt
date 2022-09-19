package com.harlanmukdis.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Movie(
    val id: Int = -1,
    val adult: Boolean? = false,
    val backdrop_path: String? = null,
    val original_language: String? = null,
    val original_title: String? = null,
    val overview: String? = null,
    val popularity: Double? = 0.0,
    val poster_path: String? = null,
    val release_date: String? = null,
    val title: String? = null,
    val video: Boolean? = false,
    val vote_average: Double? = 0.0,
    val vote_count: Int? = 0,
    val listID: Int? = -1
) : Parcelable, Serializable
