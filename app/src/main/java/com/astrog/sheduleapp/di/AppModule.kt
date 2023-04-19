package com.astrog.sheduleapp.di

import com.astrog.sheduleapp.domain.ScheduleRepository
import com.astrog.sheduleapp.domain.SearchRepository
import com.astrog.sheduleapp.internal.repository.DefaultScheduleRepository
import com.astrog.sheduleapp.internal.repository.DefaultSearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindScheduleRepository(repo: DefaultScheduleRepository): ScheduleRepository

    @Binds
    abstract fun bindSearchRepository(repo: DefaultSearchRepository): SearchRepository
}