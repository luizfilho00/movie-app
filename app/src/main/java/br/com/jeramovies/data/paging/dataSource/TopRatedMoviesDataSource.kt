package br.com.jeramovies.data.paging.dataSource

import br.com.jeramovies.domain.repository.TopRatedMoviesRepository
import kotlinx.coroutines.CoroutineScope

class TopRatedMoviesDataSource(
    override val repository: TopRatedMoviesRepository,
    override val scope: CoroutineScope,
    override val onLoading: ((Boolean) -> Unit)? = null,
    override val onFailure: ((Throwable) -> Unit)? = null
) : BaseMoviesDataSource()