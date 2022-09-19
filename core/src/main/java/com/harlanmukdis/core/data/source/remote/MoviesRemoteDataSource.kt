package com.harlanmukdis.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.harlanmukdis.core.data.source.remote.network.ApiService
import com.harlanmukdis.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZE = 25

class MoviesRemoteDataSource(
    private val apiService: ApiService
) : InterfaceMoviesRemoteDataSource {

    override fun getMovies(genreId: Int): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviesPagingSource(genreId = genreId, apiService = apiService)
            }
        ).flow
    }
}

interface InterfaceMoviesRemoteDataSource {
    fun getMovies(genreId: Int): Flow<PagingData<MovieResponse>>
}