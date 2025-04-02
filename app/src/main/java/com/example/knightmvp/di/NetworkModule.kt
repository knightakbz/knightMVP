package com.example.knightmvp.di

import android.content.Context
import com.example.knightmvp.server.interceptor.NetworkConnectionInterceptor
import com.example.knightmvp.server.interceptor.TimeoutInterceptor
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import com.example.knightmvp.BuildConfig
import com.example.knightmvp.server.interceptor.ApiLogger
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DEFAULT_TIMEOUT = 15L

    fun getHttpBilder(@ApplicationContext context: Context) {
        OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(context))
            .addInterceptor(TimeoutInterceptor())
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor(ApiLogger())
                            .setLevel(HttpLoggingInterceptor.Level.BODY)
                    )

                }
            }
            .build()
    }

}