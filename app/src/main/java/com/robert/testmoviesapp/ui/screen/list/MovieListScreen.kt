@file:OptIn(ExperimentalMaterial3Api::class)

package com.robert.testmoviesapp.ui.screen.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.robert.testmoviesapp.R
import com.robert.testmoviesapp.Screen
import com.robert.testmoviesapp.data.model.MovieListResponse
import com.robert.testmoviesapp.ui.theme.TestMoviesAppTheme

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getMovieList()
    }

    TestMoviesAppTheme {
        Scaffold(
            topBar = { MovieTopAppBar() },
            bottomBar = { BottomNavigationBar(viewModel = viewModel) },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            MovieListUi(
                viewModel = viewModel,
                navigator = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = stringResource(id = R.string.app_name)) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BottomNavigationBar(viewModel: MovieListViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = stringResource(R.string.home)
                )
            },
            label = { Text(stringResource(R.string.home)) },
            selected = uiState.selectedListId == 1,
            onClick = { viewModel.updateSelectedList(1) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.favorite)
                )
            },
            label = { Text(stringResource(R.string.favorite)) },
            selected = uiState.selectedListId == 2,
            onClick = { viewModel.updateSelectedList(2) }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GridItem(
    movie: MovieListResponse.MovieDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.padding(8.dp),
        onClick = onClick,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .aspectRatio(2 / 3f)
                    .fillMaxWidth(),
            ) {
                PosterCard(
                    movie = movie,
                    modifier = Modifier.matchParentSize(),
                )
            }

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = movie.title ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                ) {
                    ProvideTextStyle(MaterialTheme.typography.labelMedium) {
                        movie.voteAverage?.let {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Filled.Star, contentDescription = "Rating")
                                Text(
                                    text = it.toString().substring(0, 3).plus("/10"),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListUi(
    viewModel: MovieListViewModel, navigator: NavController, modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimatedVisibility(uiState.movieList != null) {
        Box(modifier = modifier) {
            LazyVerticalStaggeredGrid(modifier = Modifier.padding(horizontal = 8.dp),
                columns = StaggeredGridCells.Fixed(2),
                content = {
                    uiState.movieList?.let {
                        items(
                            count = it.results.size,
                        ) { index ->
                            val movie = it.results.get(index)
                            GridItem(movie = movie, onClick = {
                                navigator.navigate(
                                    Screen.MovieDetailScreen.withArgs(
                                        movie.id.toString(),
                                        movie.title
                                    )
                                )
                            })
                        }

                    }
                })
        }
    }
}