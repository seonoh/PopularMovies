package com.example.seonoh.popularmovies.data.api

import com.example.seonoh.popularmovies.data.vo.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path



interface TheMovieDBInterface  {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int) : Single<MovieDetails>
}