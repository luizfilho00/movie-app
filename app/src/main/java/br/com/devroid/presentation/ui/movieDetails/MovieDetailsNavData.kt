package br.com.devroid.presentation.ui.movieDetails

import android.content.Context
import br.com.devroid.presentation.util.navigation.NavData

class MovieDetailsNavData(private val movieId: Int) : NavData {

    override fun navigate(context: Context) {
        context.startActivity(MovieDetailsActivity.createIntent(context, movieId))
    }
}