package br.com.jeramovies.presentation.util.extensions

import androidx.lifecycle.LiveData
import io.realm.RealmChangeListener
import io.realm.RealmObject
import io.realm.RealmResults

class RealmLiveData<T : RealmObject>(
    private val results: RealmResults<T>
) : LiveData<RealmResults<T>>() {

    val listener = RealmChangeListener<RealmResults<T>> { results ->
        value = results
    }

    override fun onActive() {
        results.addChangeListener(listener)
    }

    override fun onInactive() {
        results.removeChangeListener(listener)
    }
}