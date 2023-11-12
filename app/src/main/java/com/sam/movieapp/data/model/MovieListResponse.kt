package com.sam.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val page: Int? = null,
    val results: List<Movie>,
)