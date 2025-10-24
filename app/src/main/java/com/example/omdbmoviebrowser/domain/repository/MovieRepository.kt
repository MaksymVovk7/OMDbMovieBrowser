package com.example.omdbmoviebrowser.domain.repository

import com.example.omdbmoviebrowser.domain.model.Movie
import com.example.omdbmoviebrowser.domain.model.MovieDetail
import com.example.omdbmoviebrowser.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun searchMovies(query: String, page: Int = 1): Resource<List<Movie>>
    suspend fun getMovieDetails(imdbId: String): Resource<MovieDetail>
    suspend fun toggleFavorite(movie: Movie)
    suspend fun toggleFavoriteDetail(movieDetail: MovieDetail)
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun isFavorite(imdbId: String): Boolean
}
