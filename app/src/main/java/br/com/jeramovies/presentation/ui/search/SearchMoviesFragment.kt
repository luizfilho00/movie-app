package br.com.jeramovies.presentation.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jeramovies.databinding.FragmentSearchBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMoviesFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
}