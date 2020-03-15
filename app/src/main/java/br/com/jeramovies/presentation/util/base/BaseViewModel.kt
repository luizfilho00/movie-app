package br.com.jeramovies.presentation.util.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.jeramovies.domain.entity.DialogData
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import br.com.jeramovies.presentation.util.livedata.SingleLiveEvent
import br.com.jeramovies.presentation.util.navigation.NavData
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val dialog: LiveData<DialogData> get() = _dialog
    val goTo: SingleLiveEvent<NavData> get() = _goTo
    val loading: LiveData<Boolean> get() = _loading

    protected val stringResource by inject<StringResource>()
    protected val _loading by lazy { MutableLiveData<Boolean>() }
    protected val config = PagedList.Config.Builder()
        .setEnablePlaceholders(true)
        .setPageSize(30)
        .build()

    private val exceptionHandler by inject<ExceptionHandler>()
    private val _dialog by lazy { MutableLiveData<DialogData>() }
    private val _goTo by lazy { SingleLiveEvent<NavData>() }

    fun goTo(navData: NavData) {
        _goTo.postValue(navData)
    }

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