package br.com.devroid.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.devroid.moviesapp.databinding.FragmentSearchBinding
import br.com.devroid.presentation.ui.main.MainViewModel
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsActivity
import br.com.devroid.presentation.ui.movies.MoviesAdapter
import br.com.devroid.presentation.util.extensions.viewLifecycle
import br.com.devroid.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMoviesFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private val activityViewModel: MainViewModel by sharedViewModel()
    private var adapter: MoviesAdapter? = null
    private var binding: FragmentSearchBinding by viewLifecycle()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
        }
        setupRecyclerView()
        subscribeUi()
        return binding.root
    }

    private fun setupRecyclerView() {
        if (adapter == null) {
            adapter = MoviesAdapter(viewModel::onMovieClicked)
        }
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun subscribeUi() {
        viewModel.searchMovies.observe(viewLifecycleOwner) { movies -> adapter?.submitList(movies) }
        viewModel.goToMovieDetails.observe(viewLifecycleOwner) { (movie, view) ->
            MovieDetailsActivity.startWithPosterTransition(
                requireActivity() as AppCompatActivity,
                view,
                movie.id
            )
        }
        activityViewModel.searchText.observe(viewLifecycleOwner, viewModel::searchMovies)
    }
}