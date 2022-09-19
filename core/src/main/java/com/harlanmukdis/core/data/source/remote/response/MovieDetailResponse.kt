package com.harlanmukdis.core.data.source.remote.response

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class MovieDetailResponse (
    @field:SerializedName("id")
    var id: Int,
    @field:SerializedName("adult")
    var adult: Boolean = false,
    @field:SerializedName("backdrop_path")
    var backdrop_path: String,
    @field:SerializedName("original_language")
    var original_language: String,
    @field:SerializedName("original_title")
    var original_title: String,
    @field:SerializedName("overview")
    var overview: String,
    @field:SerializedName("popularity")
    var popularity: Double,
    @field:SerializedName("poster_path")
    var poster_path: String,
    @field:SerializedName("release_date")
    var release_date: String,
    @field:SerializedName("title")
    var title: String,
    @field:SerializedName("video")
    var video: Boolean = false,
    @field:SerializedName("vote_average")
    var vote_average: Double,
    @field:SerializedName("vote_count")
    var vote_count: Int,
    @field:SerializedName("genre_ids")
    var genre_ids: JsonArray,
    @field:SerializedName("budget")
    var budget: Int,
    @field:SerializedName("homepage")
    var homepage: String,
    @field:SerializedName("imdb_id")
    var imdb_id: String,
    @field:SerializedName("belongs_to_collection")
    var belongs_to_collection: Collection,
    @field:SerializedName("genres")
    var genres: List<GenreResponse.Genre> = arrayListOf(),
    @field:SerializedName("production_companies")
    var production_companies: List<ProductionCompanies> = arrayListOf(),
    @field:SerializedName("production_countries")
    var production_countries: List<ProductionCountries> = arrayListOf(),
    @field:SerializedName("revenue")
    var revenue: Int,
    @field:SerializedName("runtime")
    var runtime: Int,
    @field:SerializedName("spoken_languages")
    var spoken_languages: List<Language> = arrayListOf(),
    @field:SerializedName("status")
    var status: String,
    @field:SerializedName("tagline")
    var tagline: String,
) {
    data class Collection(
        @field:SerializedName("id")
        var id: Int,
        @field:SerializedName("name")
        var name: String,
        @field:SerializedName("poster_path")
        var poster_path: String,
        @field:SerializedName("backdrop_path")
        var backdrop_path: String,
    )

    data class ProductionCompanies(
        @field:SerializedName("id")
        var id: Int,
        @field:SerializedName("name")
        var name: String,
        @field:SerializedName("logo_path")
        var logo_path: String,
        @field:SerializedName("origin_country")
        var origin_country: String,
    )

    data class ProductionCountries(
        @field:SerializedName("iso_3166_1")
        var iso_3166_1: String,
        @field:SerializedName("name")
        var name: String
    )

    data class Language(
        @field:SerializedName("iso_639_1")
        var iso_639_1: String,
        @field:SerializedName("name")
        var name: String,
        @field:SerializedName("english_name")
        var english_name: String
    )
}