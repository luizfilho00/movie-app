package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { MoviesAdapter(viewModel::loadMovies) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            vModel = viewModel
        }
        setupUi()
        setupRecyclerView()
        subscribeUi()
    }

    private fun setupUi() {
        with(binding) {
            editTextQuery.doOnTextChanged { text, _, _, _ ->
                viewModel.searchMovies(text.toString())
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.recyclerView) {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun subscribeUi() {
        viewModel.movies.observe(this, Observer { (movies, reload) ->
            adapter.submitList(movies, reload)
        })
    }
}