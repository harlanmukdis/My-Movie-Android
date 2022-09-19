package com.harlanmukdis.core.domain.usecase

import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.*
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.repository.IMainRepository
import kotlinx.coroutines.flow.Flow

class MainInteractor(private val mainRepository: IMainRepository): MainUseCase {
    override fun getGenresAndMovies(): Flow<Resource<List<Genre>>> =
        mainRepository.getGenresAndMovies()

    override fun getGenres(): Flow<Resource<List<Genre>>> =
        mainRepository.getGenres()

    override fun getDiscoverMovies(genreId: Int, page: Int) =
        mainRepository.getDiscoverMovies(genreId, page)

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailResponse>> =
        mainRepository.getMovieDetail(movieId)

    override fun getReviews(movieId: Int, page: Int): Flow<Resource<ListResponse<ReviewResponse>>> =
        mainRepository.getReviews(movieId, page)

    override fun getVideos(movieId: Int): Flow<Resource<ListResponse<VideoResponse>>> =
        mainRepository.getVideos(movieId)

    override fun getCasts(movieId: Int): Flow<Resource<CastResponse>> =
        mainRepository.getCasts(movieId)
}