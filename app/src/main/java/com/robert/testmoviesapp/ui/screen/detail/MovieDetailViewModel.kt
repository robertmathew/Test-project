package com.robert.testmoviesapp.ui.screen.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.testmoviesapp.Utils
import com.robert.testmoviesapp.data.local.Favorite
import com.robert.testmoviesapp.data.model.MovieListResponse
import com.robert.testmoviesapp.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val context: Application,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            val movie = getMovieDetails(movieId)
            _uiState.update {
                it.copy(movieDetail = movie)
            }
        }
    }

    fun updateFavoriteStatus(isFavorite: Boolean) {
        _uiState.update {
            it.copy(isMovieFavorite = isFavorite)
        }
        if (isFavorite) {
            addToFavorite(uiState.value.movieDetail!!.id)
        } else {
            removeFromFavorite(uiState.value.movieDetail!!.id)
        }
    }

    fun addToFavorite(movieId: Int) {
        favoriteRepository.addMovie(movieId)
    }

    fun removeFromFavorite(movieId: Int) {
        favoriteRepository.deleteMovie(movieId)
    }

    fun isMovieFavorite(movieId: Int) {
        favoriteRepository.getAllMovies()

        val favoriteMovies: List<Favorite> = favoriteRepository.allFavorites.value ?: emptyList()

        val isFavorite = favoriteMovies.any { it.id == movieId }
        _uiState.update {
            it.copy(isMovieFavorite = isFavorite)
        }
    }

    private fun getMovieDetails(movieId: Int): MovieListResponse.MovieDetails {
        val jsonString = Utils.readJsonFromAssets(context, "movie_list.json")
        val movieListResponse: MovieListResponse = Utils.parseJsonToMovieList(jsonString)
        val movies = movieListResponse.results.find { it.id == movieId }
        return movies!!
    }

}