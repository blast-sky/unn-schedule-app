package com.astrog.sheduleapp.di

import com.astrog.sheduleapp.internal.RuzapiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideMessagingService(retrofit: Retrofit): RuzapiClient = retrofit
        .create(RuzapiClient::class.java)

    companion object {
        const val BASE_URL = "https://portal.unn.ru/ruzapi/"
    }
}