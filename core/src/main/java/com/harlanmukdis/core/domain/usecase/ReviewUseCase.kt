package com.harlanmukdis.core.domain.usecase

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewUseCase {
    fun getReviewsByMovie(movieId: Int): Flow<PagingData<Review>>
}