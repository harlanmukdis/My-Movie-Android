package com.harlanmukdis.core.data

import androidx.paging.PagingData
import androidx.paging.map
import com.harlanmukdis.core.data.source.remote.MoviesRemoteDataSource
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.repository.IMovieRepository
import com.harlanmukdis.core.utils.DataMapperMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: MoviesRemoteDataSource
) : IMovieRepository {
    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        remoteDataSource.getMovies(genreId)
            .map { pagingData ->
                pagingData.map {
                    DataMapperMovie.mapResponseToDomain(it)
                }
            }
}