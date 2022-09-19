package com.harlanmukdis.core.domain.repository

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>>
}