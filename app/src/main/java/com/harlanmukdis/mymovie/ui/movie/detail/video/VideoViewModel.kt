package com.harlanmukdis.mymovie.ui.movie.detail.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harlanmukdis.core.data.Resource
import com.harlanmukdis.core.data.source.remote.response.CastResponse
import com.harlanmukdis.core.data.source.remote.response.VideoResponse
import com.harlanmukdis.core.domain.usecase.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(
    private val useCase: MainUseCase
): ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private val _errorMsgState = MutableStateFlow("")
    val errorMsgState: StateFlow<String> = _errorMsgState

    private val _emptyMsgState = MutableStateFlow("")
    val emptyMsgState: StateFlow<String> = _emptyMsgState

    private val _videosState = MutableStateFlow<List<VideoResponse>>(emptyList())
    val videosState: StateFlow<List<VideoResponse>> = _videosState

    fun getVideos(movieID: Int) {
        viewModelScope.launch {
            useCase.getVideos(movieID).collect {
                when (it) {
                    is Resource.Success -> {
                        _loadingState.value = false
                        if (!it.data?.results.isNullOrEmpty()) {
                            _videosState.value = it.data?.results!!
                        } else {
                            _emptyMsgState.value = "No trailer data to show"
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