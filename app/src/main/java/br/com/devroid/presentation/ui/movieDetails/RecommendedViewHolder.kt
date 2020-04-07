package br.com.devroid.presentation.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.devroid.domain.entity.Movie
import br.com.devroid.moviesapp.R
import br.com.devroid.moviesapp.databinding.ItemRecommendedMovieBinding
import com.bumptech.glide.Glide

class RecommendedViewHolder(
    private val binding: ItemRecommendedMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, onClick: (Movie) -> Unit) {
        with(binding) {
            Glide.with(root).load(movie?.getBackdropUrl())
                .fitCenter()
                .into(imageView)
            textViewMovieTitle.text = movie?.title
            textViewRating.text = movie?.voteAverage?.toString() ?: "0"
            movie?.let { root.setOnClickListener { onClick(movie) } }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = RecommendedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recommended_movie,
                parent,
                false
            )
        )
    }
}