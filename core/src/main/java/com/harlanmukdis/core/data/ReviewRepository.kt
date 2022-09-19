package com.harlanmukdis.core.data

import androidx.paging.PagingData
import androidx.paging.map
import com.harlanmukdis.core.data.source.remote.MoviesRemoteDataSource
import com.harlanmukdis.core.data.source.remote.ReviewsRemoteDataSource
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import com.harlanmukdis.core.domain.repository.IMovieRepository
import com.harlanmukdis.core.domain.repository.IReviewRepository
import com.harlanmukdis.core.utils.DataMapperMovie
import com.harlanmukdis.core.utils.DataMapperReview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReviewRepository(
    private val remoteDataSource: ReviewsRemoteDataSource
) : IReviewRepository {
    override fun getReviewByMovie(movieId: Int): Flow<PagingData<Review>> =
        remoteDataSource.getReviews(movieId)
            .map { pagingData ->
                pagingData.map {
                    DataMapperReview.mapResponseToDomain(it)
                }
            }
}