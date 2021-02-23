package com.example.seonoh.popularmovies.ui.popular_movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seonoh.popularmovies.R
import com.example.seonoh.popularmovies.data.api.TheMovieDBClient
import com.example.seonoh.popularmovies.data.api.TheMovieDBInterface
import com.example.seonoh.popularmovies.data.repository.NetworkState
import com.example.seonoh.popularmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1 else return 3
            }
        }

        binding.run {
            rvMovieList.layoutManager = gridLayoutManager
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = movieAdapter
        }

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            binding.run {
                progressBarPopular.visibility =
                    if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
                txtErrorPopular.visibility =
                    if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            }
        })
    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}