package br.com.jeramovies.presentation.ui.myList

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.domain.entity.MovieSaved

class MyListMovieAdapter(
    private val onClick: (MovieSaved) -> Unit
) : RecyclerView.Adapter<MyListMovieViewHolder>() {

    private var list = listOf<MovieSaved>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyListMovieViewHolder.inflate(parent)

    override fun onBindViewHolder(holder: MyListMovieViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    override fun getItemCount() = list.size

    fun submitList(list: List<MovieSaved>) {
        this.list = list
        notifyDataSetChanged()
    }
}