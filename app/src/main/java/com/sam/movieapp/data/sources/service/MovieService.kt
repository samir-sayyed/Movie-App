package com.sam.movieapp.data.sources.service

import com.sam.movieapp.data.model.MovieDetailsResponse
import com.sam.movieapp.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("trending/movie/day?language=en-US")
    suspend fun getMovieList(
        @Query("page") page: Int
    ): Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailsResponse>

}