package br.com.devroid.presentation.ui.rating

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import br.com.devroid.moviesapp.databinding.FragmentRatingMovieBinding
import br.com.devroid.presentation.ui.movieDetails.MovieDetailsViewModel
import br.com.devroid.presentation.util.extensions.viewLifecycle
import org.koin.android.viewmodel.ext.android.sharedViewModel

class RatingMovieFragment : DialogFragment() {

    private var binding: FragmentRatingMovieBinding by viewLifecycle()
    private val activityViewModel: MovieDetailsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatingMovieBinding.inflate(inflater, container, false)
        setupUi()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateDialog(savedInstanceState)
    }

    private fun setupUi() {
        with(binding) {
            ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
                if (fromUser) {
                    if (rating < 0.5f) ratingBar.rating = 0.5f
                }
            }
            textViewCancel.setOnClickListener { dismiss() }
            textViewSendRate.setOnClickListener {
                activityViewModel.onMovieRatingReceived(
                    ratingBar.rating * 2
                )
                dismiss()
            }
        }
    }
}