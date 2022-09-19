package com.harlanmukdis.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.harlanmukdis.core.BuildConfig
import com.harlanmukdis.core.data.source.remote.network.ApiService
import com.harlanmukdis.core.data.source.remote.response.ReviewResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1


class ReviewsPagingSource(
    private val movieId: Int,
    private val apiService: ApiService
) : PagingSource<Int, ReviewResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiService.getReviewsByMovie(
                movieId = movieId,
                apiKey = BuildConfig.API_KEY,
                language = "en-US",
                page = page
            )
            val movies = response.results
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    page + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, ReviewResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
