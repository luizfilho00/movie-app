package br.com.jeramovies.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel
    }
}