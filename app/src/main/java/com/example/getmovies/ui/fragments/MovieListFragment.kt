package com.example.getmovies.ui.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.example.getmovies.R
import com.example.getmovies.data.network.Error
import com.example.getmovies.data.network.NetworkCallStatus
import com.example.getmovies.data.network.Status
import com.example.getmovies.ui.fragments.adapters.MovieAdapter
import com.example.getmovies.ui.view_models.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

/**
 * A simple [Fragment] subclass.
 */

const val tag = "MovieListFrag_tag"

class MovieListFragment : Fragment() {

    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(movie_list) {
            adapter = MovieAdapter {
                findNavController().navigate(
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it)
                )
                viewModel.something.value = NetworkCallStatus.success()
            }
        }
        viewModel._movieList.observe(viewLifecycleOwner, Observer {
            (movie_list.adapter as MovieAdapter).submitList(it)
        })
        viewModel.something.observe(viewLifecycleOwner, Observer {

            Log.i(tag, "Observing something, ${it.msg}")
            when (it.status) {
                Status.LOADING -> {
                    Log.i(tag, "Status -> Loading")
                    progressBar.visibility = View.VISIBLE
                }

                Status.SUCCESS -> {
                    Log.i(tag, "Status -> Success")
                    progressBar.visibility = View.GONE
                }

                Status.ERROR -> {
                    Log.i(tag, "Status -> Error")
                    progressBar.visibility = View.GONE
                }
            }
            showToast("${it.msg}")

            refresh_swipe.isRefreshing = false
        })


        refresh_swipe.setOnRefreshListener {
            viewModel.refreshData()
        }


    }

    private fun showToast(msg: String) {
        if (msg != "null") {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getError(error: Error?): String {
        return when (error) {
            Error.NETWORK_ERROR -> "You might not have internet connection"
            Error.NO_DATA -> "Your Data may not be in Sync"
            else -> "Consider relaunching the app with internet"
        }
    }


}



