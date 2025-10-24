package com.example.omdbmoviebrowser.data.local.dao

import androidx.room.*
import com.example.omdbmoviebrowser.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    
    @Query("SELECT * FROM favorite_movies WHERE isFavorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>
    
    @Query("SELECT * FROM favorite_movies WHERE imdbID = :movieId")
    suspend fun getMovieById(movieId: String): MovieEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    
    @Delete
    suspend fun deleteMovie(movie: MovieEntity)
    
    @Query("DELETE FROM favorite_movies WHERE imdbID = :movieId")
    suspend fun deleteMovieById(movieId: String)
    
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movies WHERE imdbID = :movieId AND isFavorite = 1)")
    suspend fun isFavorite(movieId: String): Boolean
}
