package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.jeramovies.databinding.FragmentMainBinding
import br.com.jeramovies.presentation.ui.movies.PageAdapter
import com.google.android.material.tabs.TabLayout
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val activityViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        setupPager()
        return binding.root
    }

    private fun setupPager() {
        with(binding) {
            viewPager.adapter =
                PageAdapter(
                    requireContext(),
                    childFragmentManager
                )
            tabLayout.setupWithViewPager(viewPager)
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    activityViewModel.jumpToTop()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabSelected(tab: TabLayout.Tab?) {}
            })
        }
    }
}