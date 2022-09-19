package com.harlanmukdis.core.domain.usecase

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
}