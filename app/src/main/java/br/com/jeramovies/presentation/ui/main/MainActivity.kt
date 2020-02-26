package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.observeChanges
import br.com.jeramovies.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { MoviesAdapter(viewModel::onMovieClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            vModel = viewModel
        }
        subscribeUi()
        setupUi()
        setupRecyclerView()
    }

    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.movies.observe(this, ::onMoviesReceived)
        viewModel.searchMovies.observe(this, ::onMoviesReceived)
    }

    private fun onMoviesReceived(list: PagedList<Movie>) {
        adapter.submitList(list)
    }

    private fun setupUi() {
        with(binding) {
            searchView.observeChanges(lifecycle, viewModel::searchMovies)
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}