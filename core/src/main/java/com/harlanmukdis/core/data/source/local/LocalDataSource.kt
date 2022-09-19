package com.harlanmukdis.core.data.source.local

import com.harlanmukdis.core.data.source.local.entity.GenreEntity
import com.harlanmukdis.core.data.source.local.entity.MovieEntity
import com.harlanmukdis.core.data.source.local.room.GenreDao
import com.harlanmukdis.core.data.source.local.room.MovieDao

class LocalDataSource(private val genreDao: GenreDao,
                      private val movieDao: MovieDao) {
    fun insertGenres(genres: List<GenreEntity>) =
        genreDao.insertGenres(genres)

    fun getGenres() = genreDao.getGenres()

    fun deleteGenres() = genreDao.deleteGenres()

    fun insertDiscoverMovies(movies: List<MovieEntity>) =
        movieDao.insertDiscoverMovies(movies)

    fun getDiscoverMovies() = movieDao.getDiscoverMovies()

    fun deleteDiscoverMovies() = movieDao.deleteDiscoverMovies()
}