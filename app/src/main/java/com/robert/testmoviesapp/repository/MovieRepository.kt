package com.robert.testmoviesapp.repository

import androidx.lifecycle.MutableLiveData
import com.robert.testmoviesapp.data.local.Comment
import com.robert.testmoviesapp.data.local.CommentDao
import com.robert.testmoviesapp.data.local.Favorite
import com.robert.testmoviesapp.data.local.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieRepository(private val favoriteDao: FavoriteDao, private val commentDao: CommentDao) {

    val allFavorites = MutableLiveData<List<Favorite>>()
    val allComments = MutableLiveData<List<Comment>>()

    val _allFavorites = MutableStateFlow<List<Favorite>>(emptyList())
    val _allComments = MutableStateFlow<List<Comment>>(emptyList())

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

    fun addComment(movieId: Int, comment: String) {
        coroutineScope.launch(Dispatchers.IO) {
            commentDao.addComment(Comment(id = null, movieId = movieId, comment = comment))
        }
    }

    /*fun getAllComments() {
        coroutineScope.launch(Dispatchers.IO) {
            allComments.postValue(commentDao.getAllComment())
        }
    }*/

    fun getCommentsByMovieId(movieId: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            allComments.postValue(commentDao.findCommentByMovieId(movieId))
            _allComments.value = commentDao.findCommentByMovieId(movieId)
        }
    }

}