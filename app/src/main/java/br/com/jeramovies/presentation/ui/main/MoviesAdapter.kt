package br.com.jeramovies.presentation.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import br.com.jeramovies.domain.entity.Movie

class MoviesAdapter(
    private val fetchNext: (Int) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(DiffUtilCallback) {

    private var currentPage = 1
    private var hasMorePages = true
    private var lastItem = 0
    private var loadedList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        if (position == (itemCount - 1)) {
            currentPage++
            lastItem = itemCount - 1
            if (hasMorePages) fetchNext(currentPage)
            holder.bind(getItem(position), hasMorePages)
        } else {
            holder.bind(getItem(position))
        }
    }

    fun submitList(list: List<Movie>?, reload: Boolean = false) {
        if (reload) loadedList.clear()
        submitList(list)
    }

    override fun submitList(list: List<Movie>?) {
        list?.let(loadedList::addAll)
        super.submitList(loadedList)
        if (list.isNullOrEmpty()) {
            hasMorePages = false
            notifyItemChanged(lastItem)
        }
        if (!list.isNullOrEmpty() && list != loadedList) {
            notifyItemRangeChanged(lastItem, list.size)
        }
    }

    companion object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
            newItem.id == oldItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            newItem == oldItem
    }
}