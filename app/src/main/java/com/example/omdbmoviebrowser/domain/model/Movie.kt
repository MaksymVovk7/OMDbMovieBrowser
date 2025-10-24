package com.example.omdbmoviebrowser.domain.model

data class Movie(
    val imdbID: String,
    val title: String,
    val year: String,
    val poster: String,
    val plot: String,
    val type: String,
    val isFavorite: Boolean = false
)
