package com.example.omdbmoviebrowser.data.mapper

import com.example.omdbmoviebrowser.data.local.entity.MovieEntity
import com.example.omdbmoviebrowser.data.remote.dto.MovieDetailDto
import com.example.omdbmoviebrowser.data.remote.dto.MovieDto
import com.example.omdbmoviebrowser.domain.model.Movie
import com.example.omdbmoviebrowser.domain.model.MovieDetail


fun MovieDto.toMovie(isFavorite: Boolean = false): Movie {
    return Movie(
        imdbID = imdbID,
        title = title,
        year = year,
        poster = poster,
        plot = "",
        type = type,
        isFavorite = isFavorite
    )
}

fun MovieDetailDto.toMovieDetail(isFavorite: Boolean = false): MovieDetail {
    return MovieDetail(
        imdbID = imdbID,
        title = title,
        year = year,
        poster = poster,
        plot = plot,
        runtime = runtime,
        genre = genre,
        director = director,
        actors = actors,
        imdbRating = imdbRating,
        type = type,
        isFavorite = isFavorite
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        imdbID = imdbID,
        title = title,
        year = year,
        poster = poster,
        plot = plot ?: "",
        type = type,
        isFavorite = isFavorite
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        imdbID = imdbID,
        title = title,
        year = year,
        poster = poster,
        plot = plot,
        type = type,
        isFavorite = isFavorite
    )
}

fun MovieDetail.toMovieEntity(): MovieEntity {
    return MovieEntity(
        imdbID = imdbID,
        title = title,
        year = year,
        poster = poster,
        plot = plot,
        type = type,
        isFavorite = isFavorite
    )
}
