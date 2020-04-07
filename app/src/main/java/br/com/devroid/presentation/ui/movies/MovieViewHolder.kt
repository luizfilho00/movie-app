package br.com.devroid.presentation.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.devroid.domain.entity.Movie
import br.com.devroid.moviesapp.R
import br.com.devroid.moviesapp.databinding.ItemMovieBinding
import com.bumptech.glide.Glide
import org.koin.core.KoinComponent

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root), KoinComponent {

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
            setOnClickListener {
                movie.saved = !movie.saved
                saveToListCallback(movie)
                setImageDrawable(movie.saved)
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
                    }
                }
        }
    }

    private fun loadImage(movie: Movie?) {
        with(binding.imageView) {
            Glide.with(this)
                .load(movie?.getBackdropUrl())
                .placeholder(R.drawable.movie_empty_placeholder)
                .into(this)
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