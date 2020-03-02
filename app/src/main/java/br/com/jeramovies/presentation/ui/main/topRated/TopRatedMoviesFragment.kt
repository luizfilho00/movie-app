package br.com.jeramovies.presentation.ui.main.topRated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.databinding.FragmentMoviesBinding
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.presentation.ui.main.MoviesAdapter
import br.com.jeramovies.presentation.ui.main.MoviesViewModel
import br.com.jeramovies.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.sharedViewModel

class TopRatedMoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by sharedViewModel()
    private val adapter by lazy { MoviesAdapter(viewModel::onMovieClick) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun setupUi() {
        with(binding.recyclerView) {
            adapter = this@TopRatedMoviesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeUi() {
        with(viewModel) {
            topRatedMovies.observe(viewLifecycleOwner, ::onMoviesReceived)
            searchMovies.observe(viewLifecycleOwner) { movies ->
                if (movies.isNotEmpty()) onMoviesReceived(movies)
            }
        }
    }

    private fun onMoviesReceived(list: PagedList<Movie>) {
        adapter.submitList(list)
    }
}