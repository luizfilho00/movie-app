package br.com.devroid.presentation.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import br.com.devroid.moviesapp.R
import br.com.devroid.moviesapp.databinding.ActivityMainBinding
import br.com.devroid.presentation.ui.myList.MyListFragment
import br.com.devroid.presentation.ui.search.SearchMoviesFragment
import br.com.devroid.presentation.util.base.BaseActivity
import br.com.devroid.presentation.util.base.BaseViewModel
import br.com.devroid.presentation.util.extensions.observeChanges
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.pandora.bottomnavigator.BottomNavigator
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var navigator: BottomNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        navigator = BottomNavigator.onCreate(
            fragmentContainer = binding.fragmentContainer.id,
            bottomNavigationView = binding.bottomNavigation,
            rootFragmentsFactory = mapOf(
                R.id.action_movies to { MainFragment() },
                R.id.action_my_list to { MyListFragment() }
            ),
            defaultTab = R.id.action_movies,
            activity = this
        )
    }

    override fun onBackPressed() {
        if (!navigator.pop()) super.onBackPressed()
        else resetLayoutBehaviors()
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
            navigator.addFragment(SearchMoviesFragment())
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.observeChanges(lifecycle, viewModel::onSearchText)
        searchView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(p0: View?) {
                navigator.pop()
                resetLayoutBehaviors()
            }

            override fun onViewAttachedToWindow(p0: View?) {/* NOTHING TO DO */
            }
        })
    }

    private fun resetLayoutBehaviors() {
        val layoutParams = binding.bottomNavigation.layoutParams as CoordinatorLayout.LayoutParams
        val bottomViewNavigationBehavior = layoutParams.behavior as HideBottomViewOnScrollBehavior
        binding.appBarLayout.setExpanded(true)
        bottomViewNavigationBehavior.slideUp(binding.bottomNavigation)
    }
}