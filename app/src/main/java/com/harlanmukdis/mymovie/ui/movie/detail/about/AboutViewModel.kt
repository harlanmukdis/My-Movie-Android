package com.harlanmukdis.mymovie.ui.movie.detail.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.CastResponse
import com.harlanmukdis.core.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AboutViewModel(
    private val useCase: MainUseCase
): ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorMsgState = MutableStateFlow("")
    val errorMsgState: StateFlow<String> = _errorMsgState

    private val _emptyMsgState = MutableStateFlow("")
    val emptyMsgState: StateFlow<String> = _emptyMsgState

    private val _castsState = MutableStateFlow<List<CastResponse.Cast>>(emptyList())
    val castsState: StateFlow<List<CastResponse.Cast>> = _castsState

    fun getCasts(movieID: Int) {
        viewModelScope.launch {
            useCase.getCasts(movieID).collect {
                when (it) {
                    is Resource.Success -> {
                        _loadingState.value = false
                        if (!it.data?.cast.isNullOrEmpty()) {
                            _castsState.value = it.data?.cast!!
                        } else {
                            _emptyMsgState.value = "No cast data to show"
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