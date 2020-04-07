package br.com.devroid.presentation.ui.removeAlert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.moviesapp.databinding.FragmentRemoveMovieBinding
import br.com.devroid.presentation.ui.main.MainViewModel
import br.com.devroid.presentation.util.extensions.viewLifecycle
import br.com.devroid.presentation.util.livedata.observe
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RemoveAlertFragment : BottomSheetDialogFragment() {

    private var binding: FragmentRemoveMovieBinding by viewLifecycle()
    private val movie by lazy {
        arguments?.getSerializable(MOVIE_EXTRA) as? MovieSaved
    }
    private val viewModel: RemoveAlertViewModel by viewModel { parametersOf(movie) }
    private val activityViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemoveMovieBinding.inflate(inflater, container, false)
        setup()
        return binding.root
    }

    private fun setup() {
        binding.buttonRemove.setOnClickListener { viewModel.onRemoveClicked() }
        viewModel.toast.observe(viewLifecycleOwner) { (msg, duration) ->
            activityViewModel.showToast(msg, duration)
            movie?.let(activityViewModel::onMovieRemoved)
            dismiss()
        }
    }

    companion object {
        private const val MOVIE_EXTRA = "MOVIE_EXTRA"

        fun createInstance(movieToBeRemoved: MovieSaved) =
            RemoveAlertFragment().apply {
                arguments = bundleOf(MOVIE_EXTRA to movieToBeRemoved)
            }
    }
}