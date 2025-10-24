package com.example.omdbmoviebrowser.di

import com.example.omdbmoviebrowser.data.local.dao.MovieDao
import com.example.omdbmoviebrowser.data.remote.api.OmdbApi
import com.example.omdbmoviebrowser.data.repository.MovieRepositoryImpl
import com.example.omdbmoviebrowser.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: OmdbApi,
        dao: MovieDao,
        apiKey: String
    ): MovieRepository {
        return MovieRepositoryImpl(api, dao, apiKey)
    }
}