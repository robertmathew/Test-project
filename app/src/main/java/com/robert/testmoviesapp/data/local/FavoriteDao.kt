package com.robert.testmoviesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavorite(): List<Favorite>

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}