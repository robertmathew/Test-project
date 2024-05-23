package com.robert.testmoviesapp

sealed class Screen(val route: String) {
    object MovieListScreen: Screen("movie_list_screen")
    object MovieDetailScreen: Screen("movie_detail_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args -> append("/$args") }
        }
    }

}