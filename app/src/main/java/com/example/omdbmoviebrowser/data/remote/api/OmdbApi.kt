package com.example.omdbmoviebrowser.data.remote.api

import com.example.omdbmoviebrowser.data.remote.dto.MovieDetailDto
import com.example.omdbmoviebrowser.data.remote.dto.MovieSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbApi {
    
    @GET("/")
    suspend fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String,
        @Query("page") page: Int = 1,
        @Query("type") type: String = "movie"
    ): Response<MovieSearchResponse>
    
    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String,
        @Query("plot") plot: String = "full"
    ): Response<MovieDetailDto>
}
