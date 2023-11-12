package com.sam.movieapp.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.movieapp.data.model.Movie


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(articles: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getMovies(limit: Int, offset: Int): List<Movie>

    @Query("DELETE FROM movie")
    fun clear()
}