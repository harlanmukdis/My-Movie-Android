package com.harlanmukdis.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.harlanmukdis.core.data.source.local.LocalDataSource
import com.harlanmukdis.core.data.source.remote.MoviesPagingSource
import com.harlanmukdis.core.data.source.remote.RemoteDataSource
import com.harlanmukdis.core.data.source.remote.network.ApiResponse
import com.harlanmukdis.core.data.source.remote.response.*
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.repository.IMainRepository
import com.harlanmukdis.core.utils.AppExecutors
import com.harlanmukdis.core.utils.DataMapperGenre
import com.harlanmukdis.core.utils.DataMapperMovie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMainRepository {
    override fun getGenresAndMovies(): Flow<Resource<List<Genre>>> =
        object : NetworkBoundResource<List<Genre>, List<GenreResponse.Genre>>() {
            override fun loadFromDB(): Flow<List<Genre>> {
                return localDataSource.getGenres().map {
                    DataMapperGenre.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<GenreResponse.Genre>>> =
                remoteDataSource.getGenresAndMovies()

            override suspend fun saveCallResult(data: List<GenreResponse.Genre>) {
                val response = DataMapperGenre.mapResponseToEntities(data)
                localDataSource.insertGenres(response)
            }

        }.asFlow()

    override fun getGenres(): Flow<Resource<List<Genre>>> =
        object : NetworkBoundResource<List<Genre>, List<GenreResponse.Genre>>() {
            override fun loadFromDB(): Flow<List<Genre>> {
                return localDataSource.getGenres().map {
                    DataMapperGenre.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Genre>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<GenreResponse.Genre>>> =
                remoteDataSource.getGenres()

            override suspend fun saveCallResult(data: List<GenreResponse.Genre>) {
                val response = DataMapperGenre.mapResponseToEntities(data)
                localDataSource.insertGenres(response)
            }

        }.asFlow()

    override fun getDiscoverMovies(genreId: Int, page: Int): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getDiscoverMovies().map {
                    DataMapperMovie.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean = true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getDiscoverMovies(genreId, page)

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val responses = DataMapperMovie.mapResponsesToEntities(data)
                localDataSource.insertDiscoverMovies(responses)
            }

        }.asFlow()

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailResponse>> =
        object : OnlyNetworkBoundResource<MovieDetailResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movieId)
            }
        }.asFlow()

    override fun getReviews(movieId: Int, page: Int): Flow<Resource<ListResponse<ReviewResponse>>> =
        object : OnlyNetworkBoundResource<ListResponse<ReviewResponse>>() {
            override suspend fun createCall(): Flow<ApiResponse<ListResponse<ReviewResponse>>> {
                return remoteDataSource.getReviews(movieId, page)
            }
        }.asFlow()

    override fun getVideos(movieId: Int): Flow<Resource<ListResponse<VideoResponse>>> =
        object : OnlyNetworkBoundResource<ListResponse<VideoResponse>>() {
            override suspend fun createCall(): Flow<ApiResponse<ListResponse<VideoResponse>>> {
                return remoteDataSource.getVideos(movieId)
            }
        }.asFlow()

    override fun getCasts(movieId: Int): Flow<Resource<CastResponse>> =
        object : OnlyNetworkBoundResource<CastResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<CastResponse>> {
                return remoteDataSource.getCasts(movieId)
            }
        }.asFlow()

}