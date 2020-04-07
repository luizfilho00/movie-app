package br.com.devroid.domain.repository

import androidx.paging.DataSource
import br.com.devroid.domain.entity.Movie

interface PopularMoviesRepository : BaseMoviesRepository {

    fun getAll(): DataSource.Factory<Int, Movie>
}