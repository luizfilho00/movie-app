package br.com.devroid.presentation.util.base

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import br.com.devroid.domain.entity.DialogData
import br.com.devroid.domain.resource.StringResource
import br.com.devroid.presentation.util.exceptionHandler.ExceptionHandler
import br.com.devroid.presentation.util.livedata.SingleLiveEvent
import br.com.devroid.presentation.util.navigation.NavData
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val dialog: LiveData<DialogData> get() = _dialog
    val goTo: SingleLiveEvent<NavData> get() = _goTo
    val loading: LiveData<Boolean> get() = _loading
    val toast: LiveData<Pair<String, Int>> get() = _toast

    private val stringResource by inject<StringResource>()
    protected val _loading by lazy { MutableLiveData<Boolean>() }
    private val _toast by lazy { SingleLiveEvent<Pair<String, Int>>() }
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

    fun showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        _toast.postValue(message to duration)
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