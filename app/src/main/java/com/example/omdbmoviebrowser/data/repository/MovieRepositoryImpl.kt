package com.example.omdbmoviebrowser.data.repository

import com.example.omdbmoviebrowser.data.local.dao.MovieDao
import com.example.omdbmoviebrowser.data.mapper.toMovie
import com.example.omdbmoviebrowser.data.mapper.toMovieDetail
import com.example.omdbmoviebrowser.data.mapper.toMovieEntity
import com.example.omdbmoviebrowser.data.remote.api.OmdbApi
import com.example.omdbmoviebrowser.domain.model.Movie
import com.example.omdbmoviebrowser.domain.model.MovieDetail
import com.example.omdbmoviebrowser.domain.repository.MovieRepository
import com.example.omdbmoviebrowser.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class MovieRepositoryImpl @Inject constructor(
    private val api: OmdbApi,
    private val dao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    override suspend fun searchMovies(query: String, page: Int): Resource<List<Movie>> {
        return try {
            val response = api.searchMovies(apiKey, query, page)
            if (response.isSuccessful && response.body()?.response == "True") {
                val movies = response.body()?.search?.map { dto ->
                    val isFav = dao.isFavorite(dto.imdbID)
                    dto.toMovie(isFav)
                } ?: emptyList()
                Resource.Success(movies)
            } else {
                Resource.Error("Фільми не знайдені")
            }
        } catch (e: Exception) {
            Resource.Error("Помилка мережі: ${e.localizedMessage}")
        }
    }

    override suspend fun getMovieDetails(imdbId: String): Resource<MovieDetail> {
        return try {
            val response = api.getMovieDetails(apiKey, imdbId)
            if (response.isSuccessful && response.body()?.response == "True") {
                val movieDetail = response.body()?.let { dto ->
                    val isFav = dao.isFavorite(dto.imdbID)
                    dto.toMovieDetail(isFav)
                }
                if (movieDetail != null) {
                    Resource.Success(movieDetail)
                } else {
                    Resource.Error("Деталі фільму не знайдені")
                }
            } else {
                Resource.Error("Деталі фільму не знайдені")
            }
        } catch (e: Exception) {
            Resource.Error("Помилка мережі: ${e.localizedMessage}")
        }
    }

    override suspend fun toggleFavorite(movie: Movie) {
        if (movie.isFavorite) {
            dao.deleteMovieById(movie.imdbID)
        } else {
            dao.insertMovie(movie.copy(isFavorite = true).toMovieEntity())
        }
    }

    override suspend fun toggleFavoriteDetail(movieDetail: MovieDetail) {
        if (movieDetail.isFavorite) {
            dao.deleteMovieById(movieDetail.imdbID)
        } else {
            dao.insertMovie(movieDetail.copy(isFavorite = true).toMovieEntity())
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return dao.getFavoriteMovies().map { entities ->
            entities.map { it.toMovie() }
        }
    }

    override suspend fun isFavorite(imdbId: String): Boolean {
        return dao.isFavorite(imdbId)
    }
}
