package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.observeChanges
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        subscribeUi()
        setupUi()
        setupPager()
    }

    private fun setupPager() {
        with(binding) {
            viewPager.adapter = PageAdapter(this@MainActivity, supportFragmentManager)
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    viewModel.jumpToTop()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupUi() {
        binding.searchView.observeChanges(lifecycle, viewModel::searchMovies)
    }
}