package br.com.devroid.presentation.ui.myList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.moviesapp.databinding.FragmentMyListBinding
import br.com.devroid.presentation.ui.main.MainViewModel
import br.com.devroid.presentation.ui.removeAlert.RemoveAlertFragment
import br.com.devroid.presentation.util.extensions.viewLifecycle
import br.com.devroid.presentation.util.livedata.observe
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MyListFragment : Fragment() {

    private var binding: FragmentMyListBinding by viewLifecycle()
    private val myListMovieAdapter by lazy {
        MyListMovieAdapter(viewModel::onMovieClicked, viewModel::onMovieLongClicked)
    }
    private val viewModel: MyListViewModel by viewModel()
    private val activityViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(inflater, container, false)
        binding.run {
            lifecycleOwner = viewLifecycleOwner
            vModel = viewModel
        }
        setupRecyclerView()
        subscribeUi()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMovies()
    }

    private fun subscribeUi() {
        viewModel.savedMovies.observe(viewLifecycleOwner, myListMovieAdapter::submitList)
        viewModel.goTo.observe(viewLifecycleOwner, activityViewModel::goTo)
        viewModel.showRemoveAlert.observe(viewLifecycleOwner, ::showRemoveAlert)
        activityViewModel.movieRemoved.observe(viewLifecycleOwner, myListMovieAdapter::updateItem)
    }

    private fun showRemoveAlert(movie: MovieSaved) {
        RemoveAlertFragment.createInstance(movie).show(childFragmentManager, null)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = myListMovieAdapter
    }
}