package com.sam.movieapp.data.sources.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sam.movieapp.data.model.Movie
import com.sam.movieapp.data.sources.local.MovieDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MoviePagingSource
@Inject constructor(private val movieApi: MovieService, private val movieDao: MovieDao) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val page = params.key ?: 1
            val response = movieApi.getMovieList(page)
            val movies = response.body()?.results
            CoroutineScope(Dispatchers.IO).launch {
                if (movies != null) {
                    movieDao.insertMovie(movies)
                }
            }
            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (movies?.isEmpty() == true) null else page + 1

            return LoadResult.Page(
                data = movies!!,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
