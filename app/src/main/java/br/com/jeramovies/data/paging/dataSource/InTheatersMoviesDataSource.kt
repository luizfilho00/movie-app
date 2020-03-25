package br.com.jeramovies.data.paging.dataSource

import br.com.jeramovies.domain.repository.InTheatersMoviesRepository
import kotlinx.coroutines.CoroutineScope

class InTheatersMoviesDataSource(
    override val repository: InTheatersMoviesRepository,
    override val scope: CoroutineScope,
    override val onLoading: ((Boolean) -> Unit)? = null,
    override val onFailure: ((Throwable) -> Unit)? = null
) : BaseMoviesDataSource()