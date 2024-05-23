package com.robert.testmoviesapp.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.robert.testmoviesapp.R
import com.robert.testmoviesapp.ui.theme.TestMoviesAppTheme

@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieName: String,
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {

    viewModel.getMovieDetail(movieId)

    TestMoviesAppTheme {
        Scaffold(topBar = {
            ShowDetailsAppBar(title = movieName, onNavigateUp = { navController.popBackStack() })
        }) { innerPaddingValues ->
            MovieDetail(viewModel = viewModel, modifier = Modifier.padding(innerPaddingValues))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowDetailsAppBar(
    title: String?,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            if (title != null) {
                Text(text = title)
            }
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable { onNavigateUp() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0f),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
        ),

        modifier = modifier,
    )
}

@Composable
private fun MovieDetail(
    viewModel: MovieDetailViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(uiState.movieDetail != null) {
        Column(modifier = modifier) {
            AsyncImage(
                model = uiState.movieDetail?.backdropPath,
                contentDescription = uiState.movieDetail?.title,
                modifier = Modifier.height(200.dp),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = "Rating: " + uiState.movieDetail?.voteAverage.toString() + " / 10" + " (" + uiState.movieDetail?.voteCount + ")",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
            )

            if (uiState.movieDetail?.overview.isNullOrEmpty().not()) {
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                )
                Text(
                    uiState.movieDetail?.overview!!,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
                )
            }

            Text(
                text = "Release Date: " + uiState.movieDetail?.releaseDate,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}