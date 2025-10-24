package com.example.omdbmoviebrowser.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omdbmoviebrowser.domain.model.MovieDetail
import com.example.omdbmoviebrowser.domain.repository.MovieRepository
import com.example.omdbmoviebrowser.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieDetail = MutableLiveData<Resource<MovieDetail>>()
    val movieDetail: LiveData<Resource<MovieDetail>> = _movieDetail

    fun loadMovieDetails(imdbId: String) {
        _movieDetail.value = Resource.Loading()

        viewModelScope.launch {
            val result = repository.getMovieDetails(imdbId)
            _movieDetail.value = result
        }
    }

    fun toggleFavorite(movieDetail: MovieDetail) {
        viewModelScope.launch {
            repository.toggleFavoriteDetail(movieDetail)

            val result = repository.getMovieDetails(movieDetail.imdbID)
            _movieDetail.value = result
        }
    }
}