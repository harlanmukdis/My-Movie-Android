package com.harlanmukdis.mymovie.ui.movie.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.usecase.MovieUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieVieModel(
    private val movieUseCase: MovieUseCase
): ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _moviePageState = MutableStateFlow<PagingData<Movie>>(PagingData.empty())
    val moviePageState: StateFlow<PagingData<Movie>> = _moviePageState

    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> {
        return movieUseCase.getMoviesByGenre(genreId)
            .map { pagingData ->
                pagingData.map {
                    it
                }
            }
            .cachedIn(viewModelScope)
    }
}