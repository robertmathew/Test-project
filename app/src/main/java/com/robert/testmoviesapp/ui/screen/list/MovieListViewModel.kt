package com.robert.testmoviesapp.ui.screen.list

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
class MovieListViewModel @Inject constructor(
    private val context: Application,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.Main) {
            val movies = getMovies()

            val favoriteMovies = getFavoriteMovies(movies)

            if (_uiState.value.selectedListId == 2) {
                _uiState.update {
                    it.copy(movieList = favoriteMovies)
                }
            } else {
                _uiState.update {
                    it.copy(movieList = movies)
                }
            }
        }
    }

    fun updateSelectedList(id: Int) {
        _uiState.update {
            it.copy(selectedListId = id)
        }
        getMovieList()
    }

    private fun getMovies(): MovieListResponse {
        val jsonString = Utils.readJsonFromAssets(context, "movie_list.json")
        val movieListResponse = Utils.parseJsonToMovieList(jsonString)

        return movieListResponse
    }

    private fun getFavoriteMovies(movieList: MovieListResponse): MovieListResponse {
        favoriteRepository.getAllMovies()

        val favoriteMovieIds: List<Favorite> = favoriteRepository.allFavorites.value ?: emptyList()
        val favoriteMovies = movieList.copy(results = movieList.results.filter { movie -> movie.id in favoriteMovieIds.map { it.id } })

        return favoriteMovies
    }

}