package com.harlanmukdis.mymovie.di

import com.harlanmukdis.core.domain.usecase.*
import com.harlanmukdis.mymovie.ui.genre.GenreViewModel
import com.harlanmukdis.mymovie.ui.movie.list.MovieVieModel
import com.harlanmukdis.mymovie.ui.movie.detail.MovieDetailViewModel
import com.harlanmukdis.mymovie.ui.movie.detail.about.AboutViewModel
import com.harlanmukdis.mymovie.ui.movie.detail.review.ReviewViewModel
import com.harlanmukdis.mymovie.ui.movie.detail.video.VideoViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MainUseCase> { MainInteractor(get()) }
    factory<MovieUseCase> { MovieInteractor(get()) }
    factory<ReviewUseCase> { ReviewInteractor(get()) }
}

val viewModelModule = module {
    viewModel { GenreViewModel(get()) }
    viewModel { MovieVieModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { AboutViewModel(get()) }
    viewModel { ReviewViewModel(get()) }
    viewModel { VideoViewModel(get()) }
}