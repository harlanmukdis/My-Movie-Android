package com.harlanmukdis.mymovie.ui.movie.detail.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.CastResponse
import com.harlanmukdis.core.data.source.remote.response.ListResponse
import com.harlanmukdis.core.data.source.remote.response.ReviewResponse
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review
import com.harlanmukdis.core.domain.usecase.MainUseCase
import com.harlanmukdis.core.domain.usecase.ReviewUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val reviewUseCase: ReviewUseCase
): ViewModel() {
    fun getReviewsByMovie(movieId: Int): Flow<PagingData<Review>> {
        return reviewUseCase.getReviewsByMovie(movieId)
            .map { pagingData ->
                pagingData.map {
                    it
                }
            }
            .cachedIn(viewModelScope)
    }
}