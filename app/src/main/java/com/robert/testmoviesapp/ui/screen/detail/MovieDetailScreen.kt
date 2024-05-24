package com.robert.testmoviesapp.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.robert.testmoviesapp.R
import com.robert.testmoviesapp.data.local.Comment
import com.robert.testmoviesapp.ui.theme.TestMoviesAppTheme

@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieName: String,
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.getMovieDetail(movieId)
        viewModel.isMovieFavorite(movieId)
        viewModel.getComments(movieId)
    }

    TestMoviesAppTheme {
        Scaffold(
            topBar = {
                ShowDetailsAppBar(
                    title = movieName,
                    onNavigateUp = { navController.popBackStack() },
                )
            },
            floatingActionButton = { Fab(viewModel = viewModel, movieId = movieId) },
        )
        { innerPaddingValues ->
            MovieDetail(viewModel = viewModel, modifier = Modifier.padding(innerPaddingValues))
        }
    }
}

@Composable
private fun Fab(viewModel: MovieDetailViewModel, movieId: Int) {
    val uiState by viewModel.uiState.collectAsState()

    FloatingActionButton(
        onClick = {
            viewModel.updateFavoriteStatus(uiState.isMovieFavorite.not())
        },
    ) {
        if (uiState.isMovieFavorite) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "remove"
            )
        } else {
            Icon(imageVector = Icons.Filled.FavoriteBorder, contentDescription = "add")
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
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val comment by viewModel.comments.collectAsState(emptyList())

    AnimatedVisibility(uiState.movieDetail != null) {
        LazyColumn(modifier = modifier) {
            item {
                AsyncImage(
                    model = uiState.movieDetail?.backdropPath,
                    contentDescription = uiState.movieDetail?.title,
                    modifier = Modifier.height(200.dp),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = "Rating: " + uiState.movieDetail?.voteAverage.toString() + " / 10" + " (" + uiState.movieDetail?.voteCount + ")",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                )

                if (uiState.movieDetail?.overview.isNullOrEmpty().not()) {
                    Text(
                        text = stringResource(R.string.description),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    )
                    Text(
                        uiState.movieDetail?.overview!!,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
                    )
                }

                Text(
                    text = "Release Date: " + uiState.movieDetail?.releaseDate,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = stringResource(R.string.comments),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                )
            }

            CommentList(comments = comment)

            item {
                AddCommentView(onCommentAdded = {
                    viewModel.addComment(uiState.movieDetail!!.id, it)
                })
            }
        }


    }

}

fun LazyListScope.CommentList(
    comments: List<Comment>,
    modifier: Modifier = Modifier
) {
    items(comments.size) {
        CommentItem(comment = comments[it])
    }
}

@Composable
fun CommentItem(
    comment: Comment,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = comment.comment, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun AddCommentView(
    onCommentAdded: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var commentText by rememberSaveable {
        mutableStateOf(
            ""
        )
    }

    Column(modifier = modifier.padding(16.dp)) {
        OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            label = { Text("Add a comment") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (commentText.isNotEmpty()) {
                    onCommentAdded(commentText)
                    commentText = ""
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Add")
        }

        Spacer(modifier = Modifier.height(56.dp))
    }
}