package com.robert.testmoviesapp.ui.screen.list

import com.robert.testmoviesapp.data.model.MovieListResponse

data class MovieListUiState(
    val movieList: MovieListResponse? = null,
    val selectedListId: Int = 1,
) {

}