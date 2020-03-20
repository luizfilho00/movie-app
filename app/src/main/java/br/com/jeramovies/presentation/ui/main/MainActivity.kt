package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import br.com.jeramovies.presentation.ui.myList.MyListFragment
import br.com.jeramovies.presentation.ui.search.SearchMoviesFragment
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.observeChanges
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this)
        setSupportActionBar(binding.toolbar)
        showFragment(MainFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return menu?.let {
            menuInflater.inflate(R.menu.menu_search, menu)
            true
        } ?: super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_search) {
            setupSearchView((item.actionView as SearchView))
            showFragment(SearchMoviesFragment(), addToBackStack = true)
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_movies -> {
                showFragment(MainFragment())
                true
            }
            R.id.action_my_list -> {
                showFragment(MyListFragment())
                binding.appBarLayout.setExpanded(true)
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        if (addToBackStack)
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .addToBackStack(null)
                .commit()
        else
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .commit()
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.observeChanges(lifecycle, viewModel::onSearchText)
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                supportFragmentManager.popBackStack()
            }

            override fun onViewAttachedToWindow(p0: View?) {/* NOTHING TO DO */
            }
        })
    }
}