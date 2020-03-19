package br.com.jeramovies.presentation.ui.movieDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.databinding.ItemGenreBinding
import br.com.jeramovies.domain.entity.Genre

class MovieGenreViewHolder(
    private val binding: ItemGenreBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(genre: Genre) {
        binding.textView.text = genre.name
    }

    companion object {
        fun inflate(parent: ViewGroup) = MovieGenreViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}