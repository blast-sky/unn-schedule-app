package com.astrog.sheduleapp.internal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.astrog.sheduleapp.internal.room.converter.DateTimeConverters
import com.astrog.sheduleapp.internal.room.converter.KindOfWorkConverter
import com.astrog.sheduleapp.internal.room.entity.LessonEntity
import com.astrog.sheduleapp.internal.room.entity.StudyDayEntity

@TypeConverters(
    DateTimeConverters::class,
    KindOfWorkConverter::class,
)
@Database(
    entities = [
        LessonEntity::class,
        StudyDayEntity::class,
    ],
    version = 6,
)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun lessonDao(): LessonDao
}