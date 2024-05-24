package com.robert.testmoviesapp.data.local

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Favorite")
data class Favorite(

    @PrimaryKey
    @Nullable
    @ColumnInfo(name = "id")
    var id: Int? = null,
)