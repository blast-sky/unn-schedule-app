package com.astrog.sheduleapp.di

import com.astrog.sheduleapp.domain.ScheduleRepository
import com.astrog.sheduleapp.domain.ScheduleService
import com.astrog.sheduleapp.domain.SearchService
import com.astrog.sheduleapp.internal.room.RoomScheduleRepository
import com.astrog.sheduleapp.internal.service.DefaultScheduleService
import com.astrog.sheduleapp.internal.service.DefaultSearchService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindScheduleService(service: DefaultScheduleService): ScheduleService

    @Singleton
    @Binds
    abstract fun bindSearchService(service: DefaultSearchService): SearchService

    @Singleton
    @Binds
    abstract fun bindScheduleRepository(repo: RoomScheduleRepository): ScheduleRepository
}