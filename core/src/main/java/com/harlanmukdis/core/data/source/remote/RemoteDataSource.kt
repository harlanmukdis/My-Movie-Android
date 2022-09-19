package com.harlanmukdis.core.data.source.remote

import androidx.paging.PagingData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.harlanmukdis.core.BuildConfig
import com.harlanmukdis.core.data.source.remote.network.ApiResponse
import com.harlanmukdis.core.data.source.remote.network.ApiService
import com.harlanmukdis.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getGenresAndMovies() : Flow<ApiResponse<List<GenreResponse.Genre>>> {
        return flow<ApiResponse<List<GenreResponse.Genre>>> {
            try {
                val responseGenre = apiService.getGenres(
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US"
                )
                if (responseGenre.isSuccessful) {
                    val typeToken = object : TypeToken<GenreResponse>() {}.type
                    val resultGenre = Gson().fromJson<GenreResponse>(responseGenre.body(), typeToken)
                    val genres = arrayListOf<GenreResponse.Genre>()
                    if (resultGenre.genres.isNotEmpty()) {
                        for (genre in resultGenre.genres) {
                            val responseMovie = apiService.getDiscoverMovies(
                                apiKey = BuildConfig.API_KEY,
                                language = "en-US",
                                sortBy = "popularity.desc",
                                page = 1,
                                genreId = genre.id
                            )
                            if (responseMovie.isSuccessful) {
                                val typeToken = object : TypeToken<ListResponse<MovieResponse>>() {}.type
                                val resultMovie = Gson().fromJson<ListResponse<MovieResponse>>(
                                    responseMovie.body(),
                                    typeToken
                                )
                                genres.add(
                                    GenreResponse.Genre(
                                        id = genre.id,
                                        name = genre.name,
                                        movies = resultMovie.results
                                    )
                                )
                            } else {
                                val typeToken = object : TypeToken<ErrorResponse>() {}.type
                                val result = Gson().fromJson<ErrorResponse>(responseMovie.errorBody()?.charStream(), typeToken)
                                emit(ApiResponse.Error(result.status_message))
                            }
                            //end for
                        }
                        emit(ApiResponse.Success(genres))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(responseGenre.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGenres(): Flow<ApiResponse<List<GenreResponse.Genre>>> {
        return flow {
            try {
                val response = apiService.getGenres(
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US"
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<GenreResponse>() {}.type
                    val result = Gson().fromJson<GenreResponse>(response.body(), typeToken)
                    if (result.genres.isNotEmpty()) {
                        emit(ApiResponse.Success(result.genres))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDiscoverMovies(genreId: Int, page: Int): Flow<ApiResponse<List<MovieResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getDiscoverMovies(
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US",
                    sortBy = "popularity.desc",
                    page = page,
                    genreId = genreId
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<ListResponse<MovieResponse>>() {}.type
                    val result = Gson().fromJson<ListResponse<MovieResponse>>(response.body(), typeToken)
                    if (result.results.isNotEmpty()) {
                        emit(ApiResponse.Success(result.results))
                    } else {
                        emit(ApiResponse.Empty)
                    }
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getMovieDetail(
                    movieId = movieId,
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US"
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<MovieDetailResponse>() {}.type
                    val result = Gson().fromJson<MovieDetailResponse>(response.body(), typeToken)
                    emit(ApiResponse.Success(result))
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getReviews(movieId: Int, page: Int): Flow<ApiResponse<ListResponse<ReviewResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getReviews(
                    movieId = movieId,
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US",
                    page = page
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<ListResponse<ReviewResponse>>() {}.type
                    val result = Gson().fromJson<ListResponse<ReviewResponse>>(response.body(), typeToken)
                    emit(ApiResponse.Success(result))
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getVideos(movieId: Int): Flow<ApiResponse<ListResponse<VideoResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getVideos(
                    movieId = movieId,
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US"
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<ListResponse<VideoResponse>>() {}.type
                    val result = Gson().fromJson<ListResponse<VideoResponse>>(response.body(), typeToken)
                    emit(ApiResponse.Success(result))
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCasts(movieId: Int): Flow<ApiResponse<CastResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getCasts(
                    movieId = movieId,
                    apiKey = BuildConfig.API_KEY,
                    language = "en-US"
                )
                if (response.isSuccessful) {
                    val typeToken = object : TypeToken<CastResponse>() {}.type
                    val result = Gson().fromJson<CastResponse>(response.body(), typeToken)
                    emit(ApiResponse.Success(result))
                } else {
                    val typeToken = object : TypeToken<ErrorResponse>() {}.type
                    val result = Gson().fromJson<ErrorResponse>(response.errorBody()?.charStream(), typeToken)
                    emit(ApiResponse.Error(result.status_message))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}

