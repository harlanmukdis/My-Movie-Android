package com.harlanmukdis.core.domain.usecase

import androidx.paging.PagingData
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUseCase {
    override fun getMoviesByGenre(genreId: Int): Flow<PagingData<Movie>> =
        movieRepository.getMoviesByGenre(genreId)
}