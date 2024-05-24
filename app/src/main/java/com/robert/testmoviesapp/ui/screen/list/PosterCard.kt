package com.robert.testmoviesapp.ui.screen.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.robert.testmoviesapp.data.model.MovieListResponse

@Composable
fun PosterCard(
    movie: MovieListResponse.MovieDetails,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        PosterCardContent(movie = movie)
    }
}

@Composable
fun PosterCard(
    movie: MovieListResponse.MovieDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(onClick = onClick, modifier = modifier) {
        PosterCardContent(movie = movie)
    }
}

@Composable
private fun PosterCardContent(movie: MovieListResponse.MovieDetails) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterStart),
        )
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}