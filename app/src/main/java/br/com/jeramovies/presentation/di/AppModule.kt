package br.com.jeramovies.presentation.di

import br.com.jeramovies.BuildConfig
import br.com.jeramovies.domain.resource.StringResource
import br.com.jeramovies.presentation.ui.movies.MoviesObserver
import br.com.jeramovies.presentation.ui.movies.MoviesObserverImpl
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandler
import br.com.jeramovies.presentation.util.exceptionHandler.ExceptionHandlerImpl
import br.com.jeramovies.presentation.util.resource.AndroidStringResource
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module

val appModule = module {

    single { ExceptionHandlerImpl(get()) as ExceptionHandler }
    single { AndroidStringResource(get()) as StringResource }
    single { MoviesObserverImpl() as MoviesObserver }
    factory {
        val configBuilder = RealmConfiguration.Builder()
        Realm.getInstance(
            if (BuildConfig.DEBUG) configBuilder.deleteRealmIfMigrationNeeded().build()
            else configBuilder.build()
        )
    }
}