package com.robert.testmoviesapp.ui.screen.list

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
class MovieListViewModel @Inject constructor(
    private val context: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMovieList()
    }

    fun getMovieList() {
        viewModelScope.launch(Dispatchers.Main) {
            val movies = getMovies()
            _uiState.update {
                it.copy(movieList = movies)
            }
        }
    }

    private fun getMovies(): MovieListResponse {
        val jsonString = Utils.readJsonFromAssets(context, "movie_list.json")
        val movieListResponse = Utils.parseJsonToMovieList(jsonString)

        return movieListResponse
    }



}