package br.com.jeramovies.presentation.ui.trailer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityTrailerBinding
import br.com.jeramovies.domain.entity.Trailer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class TrailerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTrailerBinding
    private val trailer by lazy { intent.extras?.getSerializable(TRAILER_EXTRA) as Trailer }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trailer)
        lifecycle.addObserver(binding.youtubePlayerView)
        setupTrailer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun setupTrailer() {
        if (!trailer.isYoutubeVideo()) {
            Toast.makeText(
                this,
                getString(R.string.trailer_not_found),
                Toast.LENGTH_LONG
            ).apply { show() }
            finish()
        } else {
            binding.youtubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    trailer.key?.let { videoId -> youTubePlayer.loadVideo(videoId, 0f) }
                }
            })
        }
    }

    companion object {
        private const val TRAILER_EXTRA = "TRAILER_EXTRA"

        fun createIntent(context: Context, trailer: Trailer) =
            Intent(context, TrailerActivity::class.java).apply {
                putExtra(TRAILER_EXTRA, trailer)
            }
    }
}