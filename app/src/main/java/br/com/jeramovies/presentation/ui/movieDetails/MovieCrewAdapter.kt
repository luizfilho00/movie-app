package br.com.jeramovies.presentation.ui.movieDetails

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.jeramovies.domain.entity.Actor

class MovieCrewAdapter : ListAdapter<Actor, MovieCrewViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieCrewViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MovieCrewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor) = oldItem == newItem
    }
}