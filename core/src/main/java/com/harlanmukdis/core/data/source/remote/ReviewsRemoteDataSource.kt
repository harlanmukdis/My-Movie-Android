package com.harlanmukdis.core.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.harlanmukdis.core.data.source.remote.network.ApiService
import com.harlanmukdis.core.data.source.remote.response.ReviewResponse
import kotlinx.coroutines.flow.Flow

const val NETWORK_PAGE_SIZES = 10

class ReviewsRemoteDataSource(
    private val apiService: ApiService
) : InterfaceReviewsRemoteDataSource {

    override fun getReviews(movieId: Int): Flow<PagingData<ReviewResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZES,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewsPagingSource(movieId = movieId, apiService = apiService)
            }
        ).flow
    }
}

interface InterfaceReviewsRemoteDataSource {
    fun getReviews(genreId: Int): Flow<PagingData<ReviewResponse>>
}