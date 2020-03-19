package br.com.jeramovies.presentation.ui.movieDetails

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.jeramovies.domain.entity.Genre

class MovieGenresAdapter : ListAdapter<Genre, MovieGenreViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieGenreViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MovieGenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem == newItem
    }
}