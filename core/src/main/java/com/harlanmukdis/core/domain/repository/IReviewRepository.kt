package com.harlanmukdis.core.domain.repository

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import kotlinx.coroutines.flow.Flow

interface IReviewRepository {
    fun getReviewByMovie(movieId: Int): Flow<PagingData<Review>>
}