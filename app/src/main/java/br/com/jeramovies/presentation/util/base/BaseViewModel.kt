package br.com.jeramovies.presentation.util.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel : ViewModel(), KoinComponent {

    private val exceptionHandler by inject<ExceptionHandler>()

    fun <T> launchAsync(
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