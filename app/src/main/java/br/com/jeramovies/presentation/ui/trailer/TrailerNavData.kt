package br.com.jeramovies.presentation.ui.trailer

import android.content.Context
import br.com.jeramovies.domain.entity.Trailer
import br.com.jeramovies.presentation.util.navigation.NavData

class TrailerNavData(private val trailer: Trailer) : NavData {

    override fun navigate(context: Context) {
        context.startActivity(TrailerActivity.createIntent(context, trailer))
    }
}