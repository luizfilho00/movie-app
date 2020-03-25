package br.com.jeramovies.presentation.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.databinding.FragmentMoviesBinding
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieType
import br.com.jeramovies.presentation.ui.main.MainViewModel
import br.com.jeramovies.presentation.util.livedata.observe
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val moviesObserver: MoviesObserver by inject()
    private val activityViewModel: MainViewModel by sharedViewModel()
    private val viewModel: MoviesViewModel by viewModel()
    private val adapter by lazy {
        MoviesAdapter(viewModel::onMovieClick, viewModel::onSaveClicked)
    }
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
        viewModel.goTo.observe(viewLifecycleOwner, activityViewModel::goTo)
        activityViewModel.jumpToTop.observe(viewLifecycleOwner) {
            binding.recyclerView.scrollToPosition(0)
        }
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