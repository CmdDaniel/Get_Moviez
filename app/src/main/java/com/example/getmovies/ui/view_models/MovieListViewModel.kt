package com.example.getmovies.ui.view_models

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.getmovies.data.repository.MovieListRepository
import com.example.getmovies.data.network.Error
import com.example.getmovies.data.network.NetworkCallStatus
import kotlinx.coroutines.*

const val taga = "MovieList VM_tag"
class MovieListViewModel(private val applications: Application): AndroidViewModel(applications){
    private val repo: MovieListRepository =
        MovieListRepository(applications)

    var _movieList = repo.getMovies()

    var something = MutableLiveData<NetworkCallStatus>()


    init {
        Log.i(
            taga,
            "The movie list is null == ${_movieList.value?.get(1)?.title} , Making Network call()"
        )

        if (isOnline()) {
            something.value = NetworkCallStatus.success(null, "Swipe Down to get latest movies")
        } else {
            something.value = NetworkCallStatus.success(null, "Data may not be in sync")
        }
    }


    fun refreshData(){
        if(isOnline()) {
            viewModelScope.launch{
                var somwthing: NetworkCallStatus
                withContext(Dispatchers.IO){
                    repo.deleteMovies()
                    somwthing = repo.getDataFromNetwork()
                }
                something.value = somwthing
                Log.i(taga, "Value of something is = ${something.value!!.msg}")

            }
        }else{
            something.value = NetworkCallStatus.error(Error.NETWORK_ERROR,"You might not have internet")
        }

    }

    private fun isOnline(): Boolean {
        val connectivityManager =
            applications.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}