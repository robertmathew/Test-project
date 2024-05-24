package com.robert.testmoviesapp.di

import com.robert.testmoviesapp.data.local.CommentDao
import com.robert.testmoviesapp.data.local.FavoriteDao
import com.robert.testmoviesapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(favoriteDao: FavoriteDao, commentDao: CommentDao): MovieRepository {
        return MovieRepository(favoriteDao, commentDao)
    }

}