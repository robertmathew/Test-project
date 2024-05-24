package com.robert.testmoviesapp.data.local

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
data class Comment(

    @PrimaryKey(autoGenerate = true)
    @Nullable
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "movieId")
    var movieId: Int,

    @ColumnInfo(name = "comment")
    val comment: String
)
