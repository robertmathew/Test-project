package com.robert.testmoviesapp.di

import android.content.Context
import androidx.room.Room
import com.robert.testmoviesapp.data.local.CommentDao
import com.robert.testmoviesapp.data.local.FavoriteDao
import com.robert.testmoviesapp.data.local.MovieRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideFavoriteDao(appDatabase: MovieRoomDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    fun provideCommentDao(appDatabase: MovieRoomDatabase): CommentDao {
        return appDatabase.commentDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MovieRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieRoomDatabase::class.java,
            "appDB"
        ).build()
    }

}