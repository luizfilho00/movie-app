package br.com.jeramovies.presentation.ui.movies

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

    fun bind(movie: Movie?, onClick: (Movie) -> Unit) {
        setupClickListener(movie, onClick)
        loadImage(movie)
        setupProgressBar(movie)
        setupTexts(movie)
    }

    private fun setupClickListener(movie: Movie?, onClick: (Movie) -> Unit) {
        binding.root.setOnClickListener {
            movie?.let { onClick(it) }
        }
    }

    private fun setupProgressBar(movie: Movie?) {
        with(binding) {
            progressBar.progressMax = 10f
            progressBar.setProgressWithAnimation(movie?.voteAverage?.toFloat() ?: 10f, 2000L)
            textViewPercent.text = root.context.getString(R.string.percent, movie?.userScore())
        }
    }

    private fun setupTexts(movie: Movie?) {
        with(binding) {
            textViewTitle.text = movie?.title
            textViewDate.text =
                with(root.context) {
                    movie?.date()?.let { date ->
                        getString(
                            R.string.release_date,
                            date.dayOfMonth,
                            date.monthOfYear()?.asText,
                            date.year
                        )
                    } ?: getString(R.string.undefined)
                }
        }
    }

    private fun loadImage(movie: Movie?) {
        with(binding.imageView) {
            if (!movie?.backdropPath.isNullOrEmpty()) {
                load(movie?.getPosterUrl()) {
                    crossfade(true)
                    placeholder(R.drawable.movie_empty_placeholder)
                }
            } else {
                background = context.getDrawable(R.drawable.movie_empty_placeholder)
            }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) =
            MovieViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_movie,
                    parent,
                    false
                )
            )
    }
}