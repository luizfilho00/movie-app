package br.com.jeramovies.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ItemMovieBinding
import br.com.jeramovies.domain.entity.Movie
import coil.api.load

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?) {
        with(binding) {
            if (!movie?.getPosterUrl().isNullOrBlank()) {
                imageView.load(movie?.getPosterUrl()) {
                    crossfade(true)
                }
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie,
                parent,
                false
            )
        )
    }
}