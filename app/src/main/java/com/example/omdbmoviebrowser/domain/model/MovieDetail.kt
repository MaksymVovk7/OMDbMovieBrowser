package com.example.omdbmoviebrowser.domain.model

data class MovieDetail(
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val plot: String,
    val runtime: String?,
    val genre: String?,
    val director: String?,
    val actors: String?,
    val imdbRating: String?,
    val type: String,
    val isFavorite: Boolean = false
)
