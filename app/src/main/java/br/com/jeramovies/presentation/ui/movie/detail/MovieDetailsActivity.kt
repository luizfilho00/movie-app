package br.com.jeramovies.presentation.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMovieDetailsBinding
import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.util.W185
import br.com.jeramovies.domain.util.W500
import br.com.jeramovies.presentation.util.base.BaseActivity
import br.com.jeramovies.presentation.util.base.BaseViewModel
import br.com.jeramovies.presentation.util.extensions.makeStatusBarTransparent
import br.com.jeramovies.presentation.util.extensions.setupToolbar
import br.com.jeramovies.presentation.util.livedata.observe
import coil.api.load
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MovieDetailsActivity : BaseActivity() {

    override val baseViewModel: BaseViewModel get() = viewModel

    private val movieId by lazy { intent.getIntExtra(MOVIE_ID_EXTRA, 0) }
    private val viewModel: MovieDetailsViewModel by viewModel { parametersOf(movieId) }
    private val crewAdapter by lazy { MovieCrewAdapter() }
    private val genreAdapter by lazy { MovieGenresAdapter() }
    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
        binding.apply {
            lifecycleOwner = this@MovieDetailsActivity
            vModel = viewModel
        }
        makeStatusBarTransparent(window)
        setupToolbar(binding.toolbar, true)
    }

    override fun subscribeUi() {
        super.subscribeUi()
        viewModel.movieDetails.observe(this, ::setupUi)
        viewModel.movieCrew.observe(this, crewAdapter::submitList)
    }

    private fun setupUi(movie: MovieDetails) {
        binding.movie = movie
        setupToolbar(movie)
        setupToolbarImages(movie)
        setupUserRating(movie)
        setupCrewRecycler()
        setupInfos(movie)
        setupGenresRecyclerView(movie)
    }

    private fun setupToolbar(movie: MovieDetails) {
        var isShow = false
        with(binding) {
            appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                var scrollRange = -1
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.toolbar.title = movie.title
                    isShow = true
                } else if (isShow) {
                    toolbar.title = ""
                    isShow = false
                }
            })
        }
    }

    private fun setupToolbarImages(movie: MovieDetails) {
        with(binding) {
            includedMovieToolbar.imageViewToolbarBackground.load(
                movie.getPosterUrl(
                    movie.backdropPath,
                    W500
                )
            ) {
                crossfade(true)
                placeholder(R.drawable.movie_empty_placeholder)
            }
            includedMovieToolbar.imageViewPoster.load(
                movie.getPosterUrl(
                    movie.posterPath,
                    W185
                )
            ) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
            }
        }
    }

    private fun setupUserRating(movieDetails: MovieDetails) {
        with(binding.includedMovieToolbar) {
            progressBar.progressMax = 10f
            progressBar.setProgressWithAnimation(movieDetails.voteAverage?.toFloat() ?: 10f, 3000L)
            textViewPercent.text =
                root.context.getString(R.string.percent, movieDetails.userScore())
        }
    }

    private fun setupCrewRecycler() {
        with(binding) {
            recyclerView.adapter = crewAdapter
            recyclerView.layoutManager =
                LinearLayoutManager(
                    this@MovieDetailsActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
        }
    }

    private fun setupInfos(movie: MovieDetails) {
        binding.textViewReleaseDate.text = movie.date().let { date ->
            getString(
                R.string.release_date,
                date.dayOfMonth,
                date.monthOfYear()?.asText,
                date.year
            )
        }
    }

    private fun setupGenresRecyclerView(movie: MovieDetails) {
        binding.recyclerViewGenders.run {
            adapter = genreAdapter
            layoutManager = GridLayoutManager(
                this@MovieDetailsActivity,
                4
            )
        }
        genreAdapter.submitList(movie.genres)
    }

    companion object {
        const val MOVIE_ID_EXTRA = "MOVIE_ID_EXTRA"

        fun createIntent(context: Context, movieId: Int) =
            Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_EXTRA, movieId)
            }
    }
}