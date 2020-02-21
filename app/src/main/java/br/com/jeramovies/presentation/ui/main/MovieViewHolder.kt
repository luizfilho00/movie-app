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
            if (!movie?.backdropPath.isNullOrEmpty())
//                imageView.background = root.context.getDrawable(R.drawable.movie_placeholder)
                imageView.load(movie?.getPosterUrl()) {
                    crossfade(true)
                }
            progressBar.progress = movie?.voteAverage?.toFloat() ?: 100f
            progressBar.progressMax = 10f
            textViewTitle.text = movie?.title
            textViewDate.text = movie?.formattedDate()
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