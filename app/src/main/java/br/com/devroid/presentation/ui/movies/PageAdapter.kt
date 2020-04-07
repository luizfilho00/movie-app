package br.com.devroid.presentation.ui.movies

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import br.com.devroid.domain.entity.MovieType
import br.com.devroid.moviesapp.R

class PageAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MoviesFragment.createInstance(MovieType.PopularMovies)
            1 -> MoviesFragment.createInstance(MovieType.InTheatersMovies)
            2 -> MoviesFragment.createInstance(MovieType.TopRatedMovies)
            else -> throw IllegalArgumentException("That fragment not exists!")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return with(context) {
            when (position) {
                0 -> getString(R.string.popular_movies)
                1 -> getString(R.string.in_theaters_movies)
                2 -> getString(R.string.top_rated_movies)
                else -> getString(R.string.app_name)
            }
        }
    }

    override fun getCount() = 3
}