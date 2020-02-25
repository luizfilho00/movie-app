package br.com.jeramovies.presentation.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMovieDetailsBinding
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.makeStatusBarTransparent
import coil.api.load
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailsActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val movieId by lazy { intent.getIntExtra(MOVIE_ID_EXTRA, 0) }
    private val viewModel: MovieDetailsViewModel by viewModel { parametersOf(movieId) }
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        makeStatusBarTransparent(window)
        viewModel.moveDetails.observe(this, Observer {
            setupUi(it)
        })
    }

    private fun setupUi(movie: MovieDetails) {
        with(binding) {
            this.movie = movie
            imageViewToolbarBackground.load(movie.getPosterUrl(movie.backdropPath, Movie.W500))
            imageViewPoster.load(movie.getPosterUrl(movie.posterPath, Movie.W500))
        }
    }

    companion object {
        const val MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA"

        fun createIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_EXTRA, movieId)
            }
    }
}