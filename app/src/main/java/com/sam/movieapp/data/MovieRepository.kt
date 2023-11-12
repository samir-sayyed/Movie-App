package com.sam.movieapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.liveData
import com.sam.movieapp.data.sources.service.MovieService
import com.sam.movieapp.base.BaseApiRepository
import com.sam.movieapp.data.sources.local.LocalMoviePagingSource
import com.sam.movieapp.data.sources.service.MoviePagingSource
import com.sam.movieapp.data.model.Movie
import com.sam.movieapp.data.sources.local.MovieDao
import com.sam.movieapp.util.NetworkStatusHelper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    @ApplicationContext private val context: Context
) : BaseApiRepository(context) {

    fun getMovies(): LiveData<PagingData<Movie>> {
        val pagingSourceFactory: () -> PagingSource<Int, Movie> = {
            if (NetworkStatusHelper.isOnline(context)) {
                MoviePagingSource(movieService, movieDao)
            } else {
                LocalMoviePagingSource(movieDao)
            }
        }

        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }

    fun getMovieDetails(movieId: Int) = flow {
        emit(safeApiCall { movieService.getMovieDetails(movieId) })
    }

}