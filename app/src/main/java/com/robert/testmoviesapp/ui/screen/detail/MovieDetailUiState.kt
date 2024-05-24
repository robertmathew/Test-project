package com.robert.testmoviesapp.ui.screen.detail

import com.robert.testmoviesapp.data.local.Comment
import com.robert.testmoviesapp.data.model.MovieListResponse


data class MovieDetailUiState(
    val movieDetail: MovieListResponse.MovieDetails? = null,
    val isMovieFavorite: Boolean = false,
    val comments: List<Comment> = emptyList()
) {
}