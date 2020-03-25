package br.com.jeramovies.data.paging.dataSource

import br.com.jeramovies.domain.repository.PopularMoviesRepository
import kotlinx.coroutines.CoroutineScope

class PopularMoviesDataSource(
    override val repository: PopularMoviesRepository,
    override val scope: CoroutineScope,
    override val onLoading: ((Boolean) -> Unit)? = null,
    override val onFailure: ((Throwable) -> Unit)? = null
) : BaseMoviesDataSource()