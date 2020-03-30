package br.com.jeramovies.presentation.ui.removeAlert

import android.widget.Toast
import br.com.jeramovies.domain.entity.MovieSaved
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.util.base.BaseViewModel

class RemoveAlertViewModel(
    private val movie: MovieSaved,
    private val strings: StringResource,
    private val repository: MyListRepository
) : BaseViewModel() {

    fun onRemoveClicked() {
        launchAsync(
            block = { repository.remove(movie) },
            onSuccess = { deleted ->
                if (deleted) {
                    showToast(strings.movieRemovedFromList, Toast.LENGTH_SHORT)
                } else {
                    showToast(strings.moviePersistError, Toast.LENGTH_SHORT)
                }
            },
            onFailure = { showDialog(it) }
        )
    }
}