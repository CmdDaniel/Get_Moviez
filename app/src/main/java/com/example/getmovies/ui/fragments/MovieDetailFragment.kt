package com.example.getmovies.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

import com.example.getmovies.R
import com.example.getmovies.data.models.Movie
import com.example.getmovies.data.network.TmdbService
import com.example.getmovies.ui.fragments.helpers.readableFormat
import com.example.getmovies.ui.view_models.MovieDetailViewModel
import com.example.getmovies.ui.view_models.vm_factory.MovieDetailViewModelFactory
import kotlinx.android.synthetic.main.fragment_movie_detail.*

import kotlinx.android.synthetic.main.list_item.movie_title

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailFragment : Fragment() {
    private lateinit var viewModel: MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val id: Long = MovieDetailFragmentArgs.fromBundle(requireArguments()).id
        val viewModelFactory = MovieDetailViewModelFactory(id, requireActivity().application)


        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            setData(it)
        })
    }

    private fun setData(movie: Movie){

        Glide.with(this)
            .load(TmdbService.BACKDROP_BASE_URL+movie.backdropPath)
            .error(R.drawable.poster_placeholder)
            .into(movie_backdrop)

        Glide.with(this)
            .load(TmdbService.POSTER_BASE_URL+movie.posterPath)
            .error(R.drawable.poster_placeholder)
            .into(movie_poster1)

        movie_title.text = movie.title
        movie_overview.text = movie.overview

        movie_release_date.text = movie.releaseDate.readableFormat()
    }
}
