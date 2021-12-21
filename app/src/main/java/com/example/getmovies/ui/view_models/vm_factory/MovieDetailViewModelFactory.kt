package com.example.getmovies.ui.view_models.vm_factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.getmovies.ui.view_models.MovieDetailViewModel


class MovieDetailViewModelFactory(private val id: Long, private val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieDetailViewModel(id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

