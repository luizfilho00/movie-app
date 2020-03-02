package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.observeChanges
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MoviesViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            vModel = viewModel
        }
        subscribeUi()
        setupUi()
        setupPager()
    }

    private fun setupPager() {
        with(binding) {
            viewPager.adapter = PageAdapter(supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun setupUi() {
        with(binding) {
            searchView.observeChanges(lifecycle, viewModel::searchMovies)
        }
    }
}