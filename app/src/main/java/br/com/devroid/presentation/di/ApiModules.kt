package br.com.devroid.presentation.di

import br.com.devroid.data.remote.ApiService
import br.com.devroid.domain.util.PT_BR
import br.com.devroid.moviesapp.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
const val API_KEY = "89d02b6bbb3cf529a46adbee4522e5d2"
const val QUERY_INTERCEPTOR = "QUERY_INTERCEPTOR"
const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"

val apiModule = module {

    single { get<Retrofit>().create(ApiService::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(get<OkHttpClient>())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .serializeNulls()
                        .setDateFormat(API_DATE_FORMAT).create()
                )
            )
            .build()
    }

    single(named(LOGGING_INTERCEPTOR)) { HttpLoggingInterceptor().setLevel(get()) }

    single {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
            .addInterceptor(get<Interceptor>(named(QUERY_INTERCEPTOR)))
            .build()
    }

    single(named(QUERY_INTERCEPTOR)) {
        object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val newUrl = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                if (!chain.request().url.toString().contains("video"))
                    newUrl.addQueryParameter("language", PT_BR)
                return chain.proceed(chain.request().newBuilder().url(newUrl.build()).build())
            }
        }
    }
}