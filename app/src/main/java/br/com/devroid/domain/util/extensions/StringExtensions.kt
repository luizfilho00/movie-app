package br.com.devroid.domain.util.extensions

import br.com.devroid.domain.util.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(pattern: String = DATE_PATTERN): Date? {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return if (!isNullOrBlank()) formatter.parse(this) else null
}