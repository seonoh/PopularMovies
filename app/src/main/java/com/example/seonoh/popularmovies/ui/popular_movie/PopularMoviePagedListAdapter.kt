package com.example.seonoh.popularmovies.ui.popular_movie

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.seonoh.popularmovies.data.repository.NetworkState
import com.example.seonoh.popularmovies.data.vo.Movie

class PopularMoviePagedListAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }

    class MovieItemViewHolder ( view : View) : RecyclerView.ViewHolder(view){

        fun bind(movie : Movie?, context : Context){
//            itemView.rootView.cv
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