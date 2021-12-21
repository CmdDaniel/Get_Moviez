package com.example.getmovies.data.network

import com.example.getmovies.data.models.Movie

data class TmdbMovieList(
    val results: List<Movie>
)