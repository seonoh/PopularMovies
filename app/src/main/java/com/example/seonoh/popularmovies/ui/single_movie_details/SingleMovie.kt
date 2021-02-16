package com.example.seonoh.popularmovies.ui.single_movie_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.seonoh.popularmovies.R
import com.example.seonoh.popularmovies.data.api.TheMovieDBClient
import com.example.seonoh.popularmovies.data.api.TheMovieDBInterface
import com.example.seonoh.popularmovies.data.vo.MovieDetails

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel : SingleMovieViewModel
    private lateinit var movieRepository : MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId : Int =1

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
                bindUI(it)
        })
    }

    fun bindUI(it : MovieDetails){
    }

    private fun getViewModel(movieId : Int) : SingleMovieViewModel{
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository,movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}