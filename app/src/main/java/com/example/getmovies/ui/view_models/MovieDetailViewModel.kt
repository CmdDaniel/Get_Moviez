package com.example.getmovies.ui.view_models

import android.app.Application
import androidx.lifecycle.*
import com.example.getmovies.data.models.Movie
import com.example.getmovies.data.repository.MovieDetailRepository

class MovieDetailViewModel(id: Long, application: Application): ViewModel(){
    private val repo: MovieDetailRepository =
        MovieDetailRepository(application)

    val movie: LiveData<Movie> =
            repo.getMovie(id)
}