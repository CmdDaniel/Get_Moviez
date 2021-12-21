package com.example.getmovies.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.getmovies.data.models.Movie

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movie WHERE `id` = :id")
    fun getMovie(id: Long): LiveData<Movie>
}