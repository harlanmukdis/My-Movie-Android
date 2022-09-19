package com.harlanmukdis.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harlanmukdis.core.data.source.local.entity.GenreEntity
import com.harlanmukdis.core.data.source.local.entity.MovieEntity
import com.harlanmukdis.core.data.source.remote.response.GenreResponse
import com.harlanmukdis.core.data.source.remote.response.MovieResponse
import com.harlanmukdis.core.data.source.remote.response.ReviewResponse
import com.harlanmukdis.core.domain.model.Genre
import com.harlanmukdis.core.domain.model.Movie
import com.harlanmukdis.core.domain.model.Review

object DataMapperGenre {
    fun mapResponseToDomain(input: List<GenreResponse.Genre>): List<Genre> {
        val list = arrayListOf<Genre>()
        input.map { g ->
            val movies = arrayListOf<Movie>()
            g.movies?.map { m ->
                val movie = Movie(
                    id = m.id,
                    adult = m.adult,
                    backdrop_path = m.backdrop_path,
                    original_language = m.original_language,
                    original_title = m.original_title,
                    overview = m.overview,
                    popularity = m.popularity,
                    poster_path = m.poster_path,
                    release_date = m.release_date,
                    title = m.title,
                    video = m.video,
                    vote_average = m.vote_average,
                    vote_count = m.vote_count
                )
                movies.add(movie)
            }
            val genre = Genre(
                id = g.id,
                name = g.name,
                movies = movies
            )
            list.add(genre)
        }
        return list
    }

    fun mapEntitiesToDomain(input: List<GenreEntity>): List<Genre> {
        val list = arrayListOf<Genre>()
        input.map {
            val typeToken = object : TypeToken<List<Movie>>() {}.type
            val movies = Gson().fromJson<List<Movie>>(it.movies, typeToken)
            val genre = Genre(
                id = it.id,
                name = it.name,
                movies = movies
            )
            list.add(genre)
        }
        return list
    }

    fun mapResponseToEntities(input: List<GenreResponse.Genre>): List<GenreEntity> {
        val list = arrayListOf<GenreEntity>()
        input.map {
            val genre = GenreEntity(
                id = it.id,
                name = it.name,
                movies = Gson().toJson(it.movies)
            )
            list.add(genre)
        }
        return list
    }
}

object DataMapperMovie {
    fun mapResponseToDomain(input: MovieResponse): Movie {
        return Movie(
            id = input.id,
            adult = input.adult,
            backdrop_path = input.backdrop_path,
            original_language = input.original_language,
            original_title = input.original_title,
            overview = input.overview,
            popularity = input.popularity,
            poster_path = input.poster_path,
            release_date = input.release_date,
            title = input.title,
            video = input.video,
            vote_average = input.vote_average,
            vote_count = input.vote_count
        )
    }

    fun mapResponseToDomain(input: List<MovieResponse>): List<Movie> {
        val list = arrayListOf<Movie>()
        input.map {
            val movie = Movie(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
            list.add(movie)
        }
        return list
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        val list = arrayListOf<Movie>()
        input.map {
            val movie = Movie(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
            list.add(movie)
        }
        return list
    }

    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val list = arrayListOf<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                adult = it.adult,
                backdrop_path = it.backdrop_path,
                original_language = it.original_language,
                original_title = it.original_title,
                overview = it.overview,
                popularity = it.popularity,
                poster_path = it.poster_path,
                release_date = it.release_date,
                title = it.title,
                video = it.video,
                vote_average = it.vote_average,
                vote_count = it.vote_count
            )
            list.add(movie)
        }
        return list
    }
}

object DataMapperReview {
    fun mapResponseToDomain(input: ReviewResponse): Review {
        return Review(
            id = input.id,
            author = input.author,
            content = input.content,
            created_at = input.created_at,
            updated_at = input.updated_at,
            url = input.url,
            author_details = Review.Author(
                name = input.author_details.name,
                username = input.author_details.username,
                avatar_path = input.author_details.avatar_path,
                rating = input.author_details.rating
            )
        )
    }
}