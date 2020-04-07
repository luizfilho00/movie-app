package br.com.devroid.presentation.util.extensions

import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun SearchView.observeChanges(
    lifecycle: Lifecycle,
    onText: (String) -> Unit,
    debounceInMillis: Long = 500
) {
    setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            private val coroutineScope = lifecycle.coroutineScope
            private var searchJob: Job? = null

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchJob?.cancel()
                searchJob = coroutineScope.launch {
                    newText?.let {
                        delay(debounceInMillis)
                        onText(newText)
                    }
                }
                return false
            }

        }
    )
}

fun EditText.observeChanges(
    lifecycle: Lifecycle,
    onText: (String) -> Unit,
    debounceInMillis: Long = 500
) {
    val coroutineScope = lifecycle.coroutineScope
    var searchJob: Job? = null
    doOnTextChanged { text, _, _, _ ->
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(debounceInMillis)
            onText(text.toString())
        }
    }
}