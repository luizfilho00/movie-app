package br.com.jeramovies.presentation.util.resource

import android.content.Context
import br.com.jeramovies.R
import br.com.jeramovies.domain.resource.StringResource

class AndroidStringResource(
    private val context: Context
) : StringResource {

    override val ok: String get() = context.getString(R.string.ok)
    override val cancel: String get() = context.getString(R.string.cancel)
}