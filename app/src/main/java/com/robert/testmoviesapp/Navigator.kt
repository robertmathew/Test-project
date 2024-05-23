package com.robert.testmoviesapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.robert.testmoviesapp.MovieDestinationsArgs.MOVIE_ID_ARG
import com.robert.testmoviesapp.MovieDestinationsArgs.MOVIE_NAME_ARG
import com.robert.testmoviesapp.ui.screen.detail.MovieDetailScreen
import com.robert.testmoviesapp.ui.screen.list.MovieListScreen

object MovieDestinationsArgs {
    const val MOVIE_ID_ARG = "movieId"
    const val MOVIE_NAME_ARG = "movieName"
}

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MovieListScreen.route) {
        composable(route = Screen.MovieListScreen.route) {
            MovieListScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetailScreen.route + "/{$MOVIE_ID_ARG}" + "/{$MOVIE_NAME_ARG}",
            arguments = listOf(
                navArgument(MOVIE_ID_ARG) { type = NavType.StringType; defaultValue = "" },
                navArgument(MOVIE_NAME_ARG) { type = NavType.StringType; defaultValue = "" },
            )
        ) { entry ->
            MovieDetailScreen(
                movieId = entry.arguments?.getString(MOVIE_ID_ARG)?.toInt() ?: 0,
                movieName = entry.arguments?.getString(MOVIE_NAME_ARG) ?: "",
                navController = navController
            )
        }
    }
}