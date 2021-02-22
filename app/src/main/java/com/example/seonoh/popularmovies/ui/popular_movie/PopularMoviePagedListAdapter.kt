package com.example.seonoh.popularmovies.ui.popular_movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.seonoh.popularmovies.R
import com.example.seonoh.popularmovies.data.api.POSTER_BASE_URL
import com.example.seonoh.popularmovies.data.repository.NetworkState
import com.example.seonoh.popularmovies.data.vo.Movie
import com.example.seonoh.popularmovies.databinding.MovieListItemBinding
import com.example.seonoh.popularmovies.databinding.NetworkStateItemBinding
import com.example.seonoh.popularmovies.ui.single_movie_details.SingleMovie

class PopularMoviePagedListAdapter(public val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == MOVIE_VIEW_TYPE) {
            return MovieItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.movie_list_item,
                            parent,
                            false
                    )
            )
        } else {
            return NetworkStateItemViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.network_state_item,
                            parent,
                            false
                    )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        } else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }

    class MovieItemViewHolder(private val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?, context: Context) {
            binding.run {
                cvMovieTitle.text = movie?.title
                cvMovieReleaseDate.text = movie?.releaseDate

                val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
                Glide.with(binding.root.context)
                        .load(moviePosterURL)
                        .into(cvIvMoviePoster)

                root.setOnClickListener {
                    val intent = Intent(context, SingleMovie::class.java)
                    intent.putExtra("id", movie?.id)
                    context.startActivity(intent)
                }

                executePendingBindings()
            }
        }

    }

    class NetworkStateItemViewHolder(private val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(networkState: NetworkState?) {
            binding.run {
                if (networkState != null && networkState == NetworkState.LOADING) {
                    progressBarItem.visibility = View.VISIBLE
                } else {
                    progressBarItem.visibility = View.GONE
                }

                if (networkState != null && networkState == NetworkState.ERROR) {
                    errorMsgItem.visibility = View.VISIBLE
                    errorMsgItem.text = networkState.msg
                } else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                    errorMsgItem.visibility = View.VISIBLE
                    errorMsgItem.text = networkState.msg
                } else {
                    errorMsgItem.visibility = View.GONE
                }
            }

        }
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow) {
            notifyItemRemoved(super.getItemCount())
        } else {
            notifyItemInserted(super.getItemCount())
        }

    }


}
