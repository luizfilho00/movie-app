package br.com.devroid.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.devroid.domain.entity.Movie
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.moviesapp.databinding.FragmentMoviesBinding
import br.com.devroid.presentation.ui.main.MainViewModel
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsActivity
import br.com.devroid.presentation.util.extensions.viewLifecycle
import br.com.devroid.presentation.util.livedata.observe
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding by viewLifecycle()
    private val moviesObserver: MoviesObserver by inject()
    private val activityViewModel: MainViewModel by sharedViewModel()
    private val viewModel: MoviesViewModel by viewModel()
    private val adapter by lazy { MoviesAdapter(viewModel::onMovieClick) }
    private val movieType by lazy { arguments?.get(MOVIE_TYPE_EXTRA) as? MovieType }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
        }
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun setupUi() {
        with(binding.recyclerView) {
            adapter = this@MoviesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeUi() {
        moviesObserver.observeMoviesResponse(
            viewLifecycleOwner,
            movieType,
            viewModel,
            ::onMoviesReceived
        )
        viewModel.toast.observe(viewLifecycleOwner) { (msg, duration) ->
            activityViewModel.showToast(msg, duration)
        }
        viewModel.goToMovieDetails.observe(viewLifecycleOwner, ::onGoToMovieDetails)
        activityViewModel.jumpToTop.observe(viewLifecycleOwner) {
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun onGoToMovieDetails(pair: Pair<Movie, View>) {
        MovieDetailsActivity.startWithPosterTransition(
            requireActivity() as AppCompatActivity,
            pair.second,
            pair.first.id
        )
    }

    private fun onMoviesReceived(list: PagedList<Movie>) {
        adapter.submitList(list)
    }

    companion object {
        const val MOVIE_TYPE_EXTRA = "MOVIE_TYPE_EXTRA"

        fun createInstance(movieType: MovieType) =
            MoviesFragment().apply {
                arguments = bundleOf(MOVIE_TYPE_EXTRA to movieType)
            }
    }
}