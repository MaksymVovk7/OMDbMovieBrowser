package com.example.omdbmoviebrowser.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbmoviebrowser.domain.model.Movie
import com.example.omdbmoviebrowser.domain.repository.MovieRepository
import com.example.omdbmoviebrowser.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel(){

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> = _movies

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery

    init {
        searchMovies("Marvel")
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) return

        _searchQuery.value = query
        _movies.value = Resource.Loading()

        viewModelScope.launch {
            val result = repository.searchMovies(query)
            _movies.value = result
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie)
            _searchQuery.value?.let { query ->
                val result = repository.searchMovies(query)
                _movies.value = result
            }
        }
    }
}