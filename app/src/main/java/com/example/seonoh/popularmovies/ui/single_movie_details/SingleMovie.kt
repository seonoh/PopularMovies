package com.example.seonoh.popularmovies.ui.single_movie_details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.seonoh.popularmovies.R
import com.example.seonoh.popularmovies.data.api.POSTER_BASE_URL
import com.example.seonoh.popularmovies.data.api.TheMovieDBClient
import com.example.seonoh.popularmovies.data.api.TheMovieDBInterface
import com.example.seonoh.popularmovies.data.vo.MovieDetails
import com.example.seonoh.popularmovies.databinding.ActivitySingleMovieBinding
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var binding: ActivitySingleMovieBinding
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
    }

    fun bindUI(it: MovieDetails) {
        binding.run {
            movieTitle.text = it.title
            movieTagline.text = it.tagline
            movieReleaseDate.text = it.releaseDate
            movieRating.text = it.rating.toString()
            movieRuntime.text = it.runtime.toString() + " minute"

            val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
            movieBudget.text = formatCurrency.format((it.budget))
            movieRevenue.text = formatCurrency.format((it.revenue))

            val moviePosterURL = POSTER_BASE_URL + it.posterPath

        }
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]

    }
}