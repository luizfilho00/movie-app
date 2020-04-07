package br.com.devroid.presentation.ui.myList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.moviesapp.R
import br.com.devroid.moviesapp.databinding.ItemMovieMyListBinding
import com.bumptech.glide.Glide

class MyListMovieViewHolder(
    private val binding: ItemMovieMyListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movieSaved: MovieSaved,
        onClick: (MovieSaved) -> Unit,
        onLongClick: (MovieSaved) -> Unit
    ) {
        with(binding) {
            Glide.with(root)
                .load(movieSaved.posterUrl)
                .into(imageView)
            textViewMovieTitle.text = movieSaved.title
            root.setOnClickListener { onClick(movieSaved) }
            root.setOnLongClickListener {
                onLongClick(movieSaved)
                true
            }
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