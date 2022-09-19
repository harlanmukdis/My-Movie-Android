package com.harlanmukdis.mymovie.ui.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.MovieDetailResponse
import com.harlanmukdis.core.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val useCase: MainUseCase
): ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorMsgState = MutableStateFlow("")
    val errorMsgState: StateFlow<String> = _errorMsgState

    private val _movieDetailState = MutableLiveData<MovieDetailResponse>()
    val movieDetailState: LiveData<MovieDetailResponse> = _movieDetailState

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            useCase.getMovieDetail(movieId).collect {
                when (it) {
                    is Resource.Success -> {
                        _loadingState.value = false
                        if (it.data != null) {
                            _movieDetailState.value = it.data!!
                        }
                    }
                    is Resource.Error -> {
                        _loadingState.value = false
                        _errorMsgState.value = it.message!!
                    }
                    is Resource.Loading -> {
                        _loadingState.value = true
                    }
                }
            }
        }
    }
}