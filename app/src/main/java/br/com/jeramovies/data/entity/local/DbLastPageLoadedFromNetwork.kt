package br.com.jeramovies.data.entity.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DbLastPageLoadedFromNetwork(
    @PrimaryKey var id: Int = 0,
    var lastTopRatedMoviePage: Int = 1,
    var lastPopularMoviePage: Int = 1,
    var lastInTheatersMoviePage: Int = 1
) : RealmObject() {

    companion object {
        const val FIELD_PRIMARY_KEY = "page"
    }
}