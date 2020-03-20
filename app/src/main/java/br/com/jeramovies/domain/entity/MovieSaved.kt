package br.com.jeramovies.domain.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MovieSaved(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var voteAverage: Double? = 0.0,
    var posterUrl: String = ""
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        return hashCode() == other.hashCode()
    }
}