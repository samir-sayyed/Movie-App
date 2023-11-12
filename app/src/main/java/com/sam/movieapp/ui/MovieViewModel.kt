package com.sam.movieapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sam.movieapp.data.MovieRepository
import com.sam.movieapp.data.model.Movie
import com.sam.movieapp.data.model.MovieDetailsResponse
import com.sam.movieapp.util.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {


    var movieList: LiveData<PagingData<Movie>> = MutableLiveData()


    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _movieDetails = MutableLiveData<MovieDetailsResponse?>()
    val movieDetails: LiveData<MovieDetailsResponse?> = _movieDetails

    fun getMovies() = viewModelScope.launch {
        movieList = movieRepository.getMovies()
            .cachedIn(viewModelScope)
    }

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        movieRepository.getMovieDetails(movieId).collectLatest {
            when (it) {
                is ApiResult.Success -> _movieDetails.postValue(it.data)
                is ApiResult.Error -> _error.postValue(it.message)
                else -> {
                    Log.i("getMovieDetails", "getMovieDetails: Loading")
                }
            }
        }
    }

}