package com.harlanmukdis.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Genre(
    val id: Int? = -1,
    val name: String? = null,
    var movies: List<Movie>? = arrayListOf()
): Parcelable, Serializable
