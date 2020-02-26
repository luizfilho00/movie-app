package br.com.jeramovies.presentation.util.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, block: (t: T) -> Unit) {
    observe(owner, Observer { block(it) })
}