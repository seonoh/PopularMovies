package com.example.seonoh.popularmovies.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seonoh.popularmovies.data.api.POSTER_BASE_URL
import com.example.seonoh.popularmovies.data.repository.NetworkState
import com.example.seonoh.popularmovies.data.vo.Movie
import com.example.seonoh.popularmovies.databinding.MovieListItemBinding
import com.example.seonoh.popularmovies.ui.single_movie_details.SingleMovie

class PopularMoviePagedListAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }

    class MovieItemViewHolder ( private val binding : MovieListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie : Movie?, context : Context){
            binding.run {
                cvMovieTitle.text = movie?.title
                cvMovieReleaseDate.text = movie?.releaseDate

                val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
                Glide.with(binding.root.context)
                        .load(moviePosterURL)
                        .into(cvIvMoviePoster)

                root.setOnClickListener {
                    val intent = Intent(context, SingleMovie::class.java)
                    intent.putExtra("id",movie?.id)
                    context.startActivity(intent)
                }

                executePendingBindings()
            }
        }

    }

    class NetworkStateItemViewHolder ( view : View ) : RecyclerView.ViewHolder(view){
        fun bind(networkState : NetworkState?){
            if(networkState != null && networkState == NetworkState.LOADING){

            }else{

            }
        }
    }
}