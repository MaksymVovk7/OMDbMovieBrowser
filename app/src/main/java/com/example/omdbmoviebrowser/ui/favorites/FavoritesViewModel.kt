package com.example.omdbmoviebrowser.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.omdbmoviebrowser.domain.model.Movie
import com.example.omdbmoviebrowser.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val favoriteMovies = repository.getFavoriteMovies().asLiveData()

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie)
        }
    }
}