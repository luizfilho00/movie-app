package br.com.devroid.presentation.util.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewLifecycle(): ReadWriteProperty<Fragment, T> {
    return object : ReadWriteProperty<Fragment, T>, LifecycleObserver {
        private var binding: T? = null
        private var viewLifecycleOwner: LifecycleOwner? = null

        init {
            this@viewLifecycle
                .viewLifecycleOwnerLiveData
                .observe(this@viewLifecycle, Observer { newLifecycleOwner ->
                    viewLifecycleOwner
                        ?.lifecycle
                        ?.removeObserver(this)
                    viewLifecycleOwner = newLifecycleOwner.also {
                        it.lifecycle.addObserver(this)
                    }
                })
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            binding = null
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return binding!!
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            binding = value!!
        }
    }
}