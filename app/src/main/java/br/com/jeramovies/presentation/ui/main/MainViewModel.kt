package br.com.jeramovies.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.jeramovies.presentation.util.base.BaseViewModel

class MainViewModel : BaseViewModel() {

    val jumpToTop: LiveData<Boolean> get() = _jumpToTop
    val searchText: LiveData<String> get() = _searchText

    private val _searchText by lazy { MutableLiveData<String>() }
    private val _jumpToTop by lazy { MutableLiveData<Boolean>() }

    fun jumpToTop() {
        _jumpToTop.value = true
    }

    fun onSearchText(text: String) {
        _searchText.value = text
    }
}