package com.harlanmukdis.core.data.source.remote.network

import com.google.gson.JsonObject
import com.harlanmukdis.core.data.source.remote.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<JsonObject>

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): Response<JsonObject>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int,
    ): ResponseItems<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<JsonObject>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<JsonObject>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewsByMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ResponseItems<ReviewResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<JsonObject>

    @GET("movie/{movie_id}/credits")
    suspend fun getCasts(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
    ): Response<JsonObject>
}
