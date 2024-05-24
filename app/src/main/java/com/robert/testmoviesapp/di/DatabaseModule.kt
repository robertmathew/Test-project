package com.robert.testmoviesapp.di

import android.content.Context
import androidx.room.Room
import com.robert.testmoviesapp.data.local.FavoriteDao
import com.robert.testmoviesapp.data.local.FavoriteRoomDatabase
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
    fun provideFavoriteDao(appDatabase: FavoriteRoomDatabase): FavoriteDao {
        return appDatabase.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): FavoriteRoomDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            FavoriteRoomDatabase::class.java,
            "appDB"
        ).build()
    }

}