package com.example.getmovies.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.getmovies.data.models.Movie

@Dao
interface MovieListDao{
    @Query("SELECT * FROM movie ORDER BY release_date desc")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("delete from movie")
    suspend fun deleteAll()

}