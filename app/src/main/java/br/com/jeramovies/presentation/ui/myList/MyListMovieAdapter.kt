package br.com.jeramovies.presentation.ui.myList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.jeramovies.domain.entity.MovieSaved

class MyListMovieAdapter : ListAdapter<MovieSaved, MyListMovieViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyListMovieViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MyListMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<MovieSaved>() {
        override fun areItemsTheSame(oldItem: MovieSaved, newItem: MovieSaved) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieSaved, newItem: MovieSaved) =
            oldItem == newItem
    }
}