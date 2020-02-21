package br.com.jeramovies.domain.util.extensions

import br.com.jeramovies.domain.entity.Movie
import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(pattern: String = Movie.DATE_PATTERN): Date {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.parse(this) as Date
}