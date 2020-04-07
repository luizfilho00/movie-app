package br.com.devroid.presentation.ui.movieDetails

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.devroid.domain.entity.Movie

class RecommendedAdapter(
    private val onClick: (Movie) -> Unit
) : PagedListAdapter<Movie, RecommendedViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecommendedViewHolder.inflate(
            parent
        )

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            newItem == oldItem
    }
}