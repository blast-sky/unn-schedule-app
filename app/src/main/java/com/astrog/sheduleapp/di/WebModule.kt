package com.astrog.sheduleapp.di

import com.astrog.sheduleapp.internal.service.RuzapiClient
import com.astrog.sheduleapp.internal.service.RuzapiClient.Companion.BASE_URL
import com.astrog.sheduleapp.internal.service.typeAdapter.EnumConverterFactory
import com.astrog.sheduleapp.internal.service.typeAdapter.LocalDateSerializer
import com.astrog.sheduleapp.internal.service.typeAdapter.LocalTimeSerializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.oshai.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Singleton

private val logger = KotlinLogging.logger { }

@Module
@InstallIn(SingletonComponent::class)
object WebModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer())
            .create()

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { logger.info { it } }
                .apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(EnumConverterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideMessagingService(retrofit: Retrofit): RuzapiClient = retrofit
        .create(RuzapiClient::class.java)
}