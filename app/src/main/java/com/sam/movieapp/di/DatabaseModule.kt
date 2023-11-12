package com.sam.movieapp.di

import android.content.Context
import androidx.room.Room
import com.sam.movieapp.data.sources.local.AppDatabase
import com.sam.movieapp.data.sources.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "movie_database").build()


    @Singleton
    @Provides
    fun provideArticleDao(db: AppDatabase): MovieDao = db.getMovieDao()

}