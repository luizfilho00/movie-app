package br.com.jeramovies.presentation.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.jeramovies.presentation.ui.main.latest.NowPlayingMoviesFragment
import br.com.jeramovies.presentation.ui.main.popular.PopularMoviesFragment
import br.com.jeramovies.presentation.ui.main.topRated.TopRatedMoviesFragment

class PageAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> PopularMoviesFragment()
            1 -> NowPlayingMoviesFragment()
            2 -> TopRatedMoviesFragment()
            else -> throw Exception("That fragment not exists!")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Em Alta"
            1 -> "Em Exibição"
            2 -> "Mais votados"
            else -> "Movies"
        }
    }

    override fun getCount() = 3
}