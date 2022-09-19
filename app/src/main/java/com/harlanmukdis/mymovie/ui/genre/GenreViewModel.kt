package com.harlanmukdis.mymovie.ui.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GenreViewModel(
    private val useCase: MainUseCase
): ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorMsgState = MutableStateFlow("")
    val errorMsgState: StateFlow<String> = _errorMsgState

    private val _genresState = MutableStateFlow<List<Genre>>(emptyList())
    val genresState: StateFlow<List<Genre>> = _genresState

    init {
        getGenresAndMovie()
    }

    fun getGenresAndMovie() {
        viewModelScope.launch {
            useCase.getGenresAndMovies().collect {
                when (it) {
                    is Resource.Success -> {
                        _loadingState.value = false
                        if (it.data!!.isNotEmpty()) {
                            _genresState.value = it.data!!
                        } else {
                            _errorMsgState.value = "Data not found"
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