package com.harlanmukdis.core.domain.usecase

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import com.harlanmukdis.core.domain.repository.IMovieRepository
import com.harlanmukdis.core.domain.repository.IReviewRepository
import kotlinx.coroutines.flow.Flow

class ReviewInteractor(private val reviewRepository: IReviewRepository): ReviewUseCase {
    override fun getReviewsByMovie(movieId: Int): Flow<PagingData<Review>> =
        reviewRepository.getReviewByMovie(movieId)
}