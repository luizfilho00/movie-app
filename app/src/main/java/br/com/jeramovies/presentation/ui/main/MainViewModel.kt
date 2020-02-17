package br.com.jeramovies.presentation.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jeramovies.domain.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MoviesRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            Log.d("GetMovies", repository.getMovies().toString())
        }
    }
}