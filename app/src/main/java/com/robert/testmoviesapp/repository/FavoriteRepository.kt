package com.robert.testmoviesapp.repository

import androidx.lifecycle.MutableLiveData
import com.robert.testmoviesapp.data.local.Favorite
import com.robert.testmoviesapp.data.local.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    val allFavorites = MutableLiveData<List<Favorite>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addMovie(movieId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            favoriteDao.addFavorite(Favorite(movieId))
        }
    }

    fun getAllMovies() {
        coroutineScope.launch(Dispatchers.IO) {
            allFavorites.postValue(favoriteDao.getAllFavorite())
        }
    }

    fun deleteMovie(movieId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            favoriteDao.deleteFavorite(Favorite(movieId))
        }
    }


}