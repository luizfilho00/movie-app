package br.com.jeramovies.presentation.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jeramovies.domain.entity.DialogData
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val dialog: LiveData<DialogData> get() = _dialog

    protected val stringResource by inject<StringResource>()

    private val exceptionHandler by inject<ExceptionHandler>()
    private val _dialog by lazy { MutableLiveData<DialogData>() }

    protected fun showDialog(
        message: String,
        title: String? = null,
        confirmText: String? = stringResource.ok,
        cancelText: String? = null,
        onConfirm: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        _dialog.postValue(
            DialogData(message, title, confirmText, cancelText, onDismiss, onConfirm)
        )
    }

    protected fun showDialog(
        throwable: Throwable,
        title: String? = null,
        confirmText: String? = stringResource.ok,
        cancelText: String? = null,
        onConfirm: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        _dialog.postValue(
            DialogData(
                exceptionHandler.resolveExceptionMessage(throwable),
                title, confirmText, cancelText,
                onDismiss, onConfirm
            )
        )
    }

    protected fun <T> launchAsync(
        block: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFailure: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch {
            runCatching { block() }
                .onSuccess {
                    onSuccess(it)
                }
                .onFailure {
                    onFailure?.invoke(exceptionHandler.resolveExceptionMessage(it))
                }
        }
    }
}