package com.example.getmovies.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.getmovies.data.models.Movie
import com.example.getmovies.data.database.MovieDatabase
import com.example.getmovies.data.database.MovieListDao
import com.example.getmovies.data.network.Error
import com.example.getmovies.data.network.NetworkCallStatus
import com.example.getmovies.data.network.TmdbService
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

const val tag = "Repo"

class MovieListRepository(context: Application) {
    private val movieListDao: MovieListDao = MovieDatabase.getDatabase(context).movieListDao()
    private val tmdbService by lazy { TmdbService.getInstance() }

    suspend fun getDataFromNetwork():NetworkCallStatus{

        try {
            val response = tmdbService.getMovies()
            val listOfMoviesResponse = response.body()
            return if (response.isSuccessful && listOfMoviesResponse != null) {
                movieListDao.insertMovies(listOfMoviesResponse.results)
                Log.i(tag, "Response successful")
                NetworkCallStatus.success(null,"You are now viewing the latest movies")
            } else {
                Log.i(tag, "Response not successful")
                NetworkCallStatus.error(Error.NO_DATA,"Response not successful from server")
            }
        } catch (e: IOException) {
            Log.i(tag, "IO Exception, You might not have Internet Connection")
            return NetworkCallStatus.error(Error.NETWORK_ERROR,"You might not have internet...")
        } catch (e: HttpException) {
            Log.i(tag, "Http Exception. Unexpected Response from server")
            return NetworkCallStatus.error(Error.SERVER_ERROR,"Unexpected Response from server")
        }catch (e: Exception){
            Log.i(tag, "UnKnown Error")
            return NetworkCallStatus.error(Error.UNKNOWN_ERROR,"UnKnown Error")
        }
    }



    fun getMovies(): LiveData<List<Movie>> {
        return movieListDao.getMovies()
    }

    suspend fun deleteMovies(){
        return movieListDao.deleteAll()
    }


}

