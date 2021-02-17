package com.example.seonoh.popularmovies.data.api

import com.example.seonoh.popularmovies.data.vo.MovieDetails
import com.example.seonoh.popularmovies.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TheMovieDBInterface  {

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int) : Single<MovieDetails>

    @GET("movie/popular")
    fun getPopularMovie(@Query("page") page:Int) : Single<MovieResponse>
}