package com.sam.movieapp.data.sources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sam.movieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(
    TypeConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}