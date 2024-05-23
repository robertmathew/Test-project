package com.robert.testmoviesapp.ui.screen.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robert.testmoviesapp.Utils
import com.robert.testmoviesapp.model.MovieListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val context: Application
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

    private fun getMovieDetails(movieId: Int): MovieListResponse.MovieDetails {
        val jsonString = Utils.readJsonFromAssets(context, "movie_list.json")
        val movieListResponse: MovieListResponse = Utils.parseJsonToMovieList(jsonString)
        val movies = movieListResponse.results.find { it.id == movieId }
        return movies!!
    }

}