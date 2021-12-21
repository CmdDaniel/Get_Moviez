package com.example.getmovies.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.getmovies.data.models.Movie
import com.example.getmovies.data.database.MovieDatabase
import com.example.getmovies.data.database.MovieDetailDao

class MovieDetailRepository(context: Application){
    private val movieDetailDao: MovieDetailDao = MovieDatabase.getDatabase(context).movieDetailDao()

    fun getMovie(id: Long): LiveData<Movie> {
        return movieDetailDao.getMovie(id)
    }

}