package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.harlanmukdis.core.domain.model.Movie
import java.io.Serializable

data class GenreResponse(
    @field:SerializedName("genres")
    val genres: List<Genre>
) {
    data class Genre(
        @field:SerializedName("id")
        val id: Int,
        @field:SerializedName("name")
        val name: String,
        var movies: List<MovieResponse>? = arrayListOf()
    )
}
