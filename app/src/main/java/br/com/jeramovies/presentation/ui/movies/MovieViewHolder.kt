package br.com.jeramovies.presentation.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ItemMovieBinding
import br.com.jeramovies.domain.entity.Movie
import coil.api.load

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, onClick: (Movie) -> Unit, saveToListCallback: (Movie) -> Unit) {
        setupClickListener(movie, onClick, saveToListCallback)
        loadImage(movie)
        setupProgressBar(movie)
        setupTexts(movie)
    }

    private fun setupClickListener(
        movie: Movie?,
        onClick: (Movie) -> Unit,
        saveToListCallback: (Movie) -> Unit
    ) {
        movie?.let {
            binding.root.setOnClickListener {
                onClick(movie)
            }
            with(binding.imageViewSave) {
                setImageDrawable(movie.saved)
                setupImageSaveClickListener(movie, this, saveToListCallback)
            }
        }
    }

    private fun ImageView.setImageDrawable(saved: Boolean?) {
        setImageDrawable(context.getDrawable(if (saved == true) R.drawable.ic_saved else R.drawable.ic_save))
    }

    private fun setupImageSaveClickListener(
        movie: Movie, imageView: ImageView, saveToListCallback: (Movie) -> Unit
    ) {
        with(imageView) {
            setOnClickListener { _ ->
                movie.saved = !movie.saved
                setImageDrawable(movie.saved)
                saveToListCallback(movie)
            }
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
                load(movie?.getBackdropUrl()) {
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