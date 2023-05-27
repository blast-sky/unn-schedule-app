package com.astrog.sheduleapp.internal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.astrog.sheduleapp.internal.room.entity.StudyDayEntity
import com.astrog.sheduleapp.internal.room.entity.LessonEntity

@TypeConverters(Converters::class)
@Database(
    entities = [LessonEntity::class, StudyDayEntity::class],
    version = 4,
)
abstract class ScheduleDatabase : RoomDatabase() {

    abstract fun lessonDao(): LessonDao
}