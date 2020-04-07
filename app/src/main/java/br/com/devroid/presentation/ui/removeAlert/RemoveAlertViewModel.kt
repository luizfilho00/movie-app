package br.com.devroid.presentation.ui.removeAlert

import android.widget.Toast
import br.com.devroid.domain.entity.MovieSaved
import br.com.devroid.domain.repository.MyListRepository
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.util.base.BaseViewModel

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