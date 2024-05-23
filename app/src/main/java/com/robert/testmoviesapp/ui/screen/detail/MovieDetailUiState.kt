package com.robert.testmoviesapp.ui.screen.detail

import com.robert.testmoviesapp.model.MovieListResponse


data class MovieDetailUiState(
    val movieDetail: MovieListResponse.MovieDetails? = null
) {
}