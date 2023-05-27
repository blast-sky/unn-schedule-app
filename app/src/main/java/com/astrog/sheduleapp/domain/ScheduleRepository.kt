package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.Cache
import com.astrog.sheduleapp.domain.model.Lesson
import com.astrog.sheduleapp.domain.model.StudyDay
import com.astrog.sheduleapp.internal.dto.LessonDto
import com.astrog.sheduleapp.internal.room.entity.StudyDayWithLessonsEntity
import java.time.LocalDate

interface ScheduleRepository{

    suspend fun getCachedDay(objectId: Long, date: LocalDate): Cache<StudyDayWithLessonsEntity>?

    suspend fun deleteAllCachedDays(objectId: Long)

    suspend fun saveStudyDays(objectId: Long, studyDays: List<StudyDay>)
}