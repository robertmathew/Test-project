package com.robert.testmoviesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: Comment)

    @Query("SELECT * FROM comment")
    suspend fun getAllComment(): List<Comment>

    @Query("SELECT * FROM comment WHERE movieId = :movieId")
    suspend fun findCommentByMovieId(movieId: Int): List<Comment>

    @Delete
    suspend fun deleteComment(favorite: Comment)
}
