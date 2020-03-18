package br.com.jeramovies.presentation.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ItemMovieMyListBinding
import br.com.jeramovies.domain.entity.MovieSaved
import coil.api.load

class MyListMovieViewHolder(
    private val binding: ItemMovieMyListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movieSaved: MovieSaved,
        onClick: (MovieSaved) -> Unit
    ) {
        with(binding) {
            imageView.load(movieSaved.posterUrl)
            textViewMovieTitle.text = movieSaved.title
            root.setOnClickListener { onClick(movieSaved) }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = MyListMovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_movie_my_list,
                parent,
                false
            )
        )
    }
}