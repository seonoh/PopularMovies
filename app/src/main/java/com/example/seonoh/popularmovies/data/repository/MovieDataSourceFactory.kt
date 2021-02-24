package com.example.seonoh.popularmovies.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.seonoh.popularmovies.data.api.TheMovieDBInterface
import com.example.seonoh.popularmovies.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): androidx.paging.DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}