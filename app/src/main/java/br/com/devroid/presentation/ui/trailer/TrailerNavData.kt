package br.com.devroid.presentation.ui.trailer

import android.content.Context
import br.com.devroid.domain.entity.Trailer
import br.com.devroid.presentation.util.navigation.NavData

class TrailerNavData(private val trailer: Trailer) : NavData {

    override fun navigate(context: Context) {
        context.startActivity(TrailerActivity.createIntent(context, trailer))
    }
}