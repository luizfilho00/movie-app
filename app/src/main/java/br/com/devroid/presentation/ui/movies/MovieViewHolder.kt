package br.com.devroid.presentation.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.util.W185
import br.com.devroid.domain.util.W500
import br.com.devroid.moviesapp.R
import br.com.devroid.moviesapp.databinding.ItemMovieBinding
import br.com.devroid.presentation.util.extensions.setVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import org.koin.core.KoinComponent

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root), KoinComponent {

    fun bind(
        movie: Movie?,
        onClick: (Movie, View) -> Unit,
        saveToListCallback: (Movie) -> Unit,
        hideSaveButton: Boolean = false
    ) {
        binding.imageViewSave.setVisible(!hideSaveButton)
        setupClickListener(movie, onClick, saveToListCallback)
        loadImage(movie)
        setupProgressBar(movie)
        setupTexts(movie)
    }

    private fun setupClickListener(
        movie: Movie?,
        onClick: (Movie, View) -> Unit,
        saveToListCallback: (Movie) -> Unit
    ) {
        movie?.let {
            binding.root.setOnClickListener {
                onClick(movie, binding.imageViewPoster)
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
            textViewResume.text = movie?.overview
        }
    }

    private fun loadImage(movie: Movie?) {
        with(binding.imageViewPoster) {
            Glide.with(this)
                .load(movie?.getPosterUrl(movie.posterPath, W500))
                .placeholder(R.drawable.poster_placeholder)
                .apply(RequestOptions().apply { transform(CenterCrop(), RoundedCorners(8)) })
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