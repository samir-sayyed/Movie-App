package com.sam.movieapp.data.model

data class MovieDetailsResponse(
    val adult: Boolean? = null,
    val backdrop_path: String? = null,
    val budget: Int? = null,
    val genres: List<Genre?>? = null,
    val homepage: String? = null,
    val id: Int? = null,
    var original_language: String? = null,
    val original_title: String? = null,
    var overview: String? = null,
    val popularity: Double? = null,
    var poster_path: String? = null,
    var release_date: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val status: String? = null,
    val tagline: String? = null,
    var title: String? = null,
    val video: Boolean? = null,
    var vote_average: Double? = null,
    val vote_count: Int? = null
){
    data class Genre(
        val id: Int? = null,
        val name: String? = null
    )
}