package com.harlanmukdis.core.domain.repository

import androidx.paging.PagingData
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.*
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMainRepository {
    fun getGenresAndMovies(): Flow<Resource<List<Genre>>>

    fun getGenres(): Flow<Resource<List<Genre>>>

    fun getDiscoverMovies(genreId: Int, page: Int): Flow<Resource<List<Movie>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailResponse>>

    fun getReviews(movieId: Int, page: Int): Flow<Resource<ListResponse<ReviewResponse>>>

    fun getVideos(movieId: Int): Flow<Resource<ListResponse<VideoResponse>>>

    fun getCasts(movieId: Int): Flow<Resource<CastResponse>>
}