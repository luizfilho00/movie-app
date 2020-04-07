package br.com.devroid

import android.app.Application
import br.com.devroid.presentation.di.apiModule
import br.com.devroid.presentation.di.appModule
import br.com.devroid.presentation.di.repositoryModule
import br.com.devroid.presentation.di.viewModelModule
import com.facebook.stetho.Stetho
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        JodaTimeAndroid.init(this)
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    appModule
                )
            )
        }
    }
}