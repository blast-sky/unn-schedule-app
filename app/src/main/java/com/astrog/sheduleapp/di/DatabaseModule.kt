package com.astrog.sheduleapp.di

import android.content.Context
import androidx.room.Room
import com.astrog.sheduleapp.internal.room.LessonDao
import com.astrog.sheduleapp.internal.room.ScheduleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext applicationContext: Context): ScheduleDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ScheduleDatabase::class.java,
            "schedule.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideLessonDao(database: ScheduleDatabase) : LessonDao {
        return database.lessonDao()
    }
}