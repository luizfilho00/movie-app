package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ActivityMainBinding
import coil.api.load
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel.movies.observe(this, Observer {
            binding.imageView.load("https://image.tmdb.org/t/p/w500${it.firstOrNull()?.posterPath}")
        })
    }
}