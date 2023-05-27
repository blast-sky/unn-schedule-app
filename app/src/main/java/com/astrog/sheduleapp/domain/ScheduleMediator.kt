package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.Cache
import com.astrog.sheduleapp.domain.model.Lesson
import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.internal.dto.LessonDto
import java.time.LocalDate
import javax.inject.Inject

class ScheduleMediator @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val scheduleService: ScheduleService,
) {

    suspend fun getLessons(
        date: LocalDate,
        objectId: Long,
        objectType: ScheduleType,
    ): List<Lesson> {
        return when (val cache = scheduleRepository.getCachedDay(objectId, date)) {
            is Cache.Fresh -> {
                cache.data.lessons
            }

            is Cache.Obsolete -> {
                scheduleRepository.deleteAllCachedDays(objectId)
                fetchAndSaveLessonsRange(date, objectId, objectType)
            }

            null -> {
                fetchAndSaveLessonsRange(date, objectId, objectType)
            }
        }
    }

    private suspend fun fetchAndSaveLessonsRange(
        date: LocalDate,
        objectId: Long,
        objectType: ScheduleType,
    ): List<Lesson> {
        val fetchedDays = scheduleService.getSchedule(
            objectType,
            objectId,
            date.minusDays(batchSizeHalf),
            date.plusDays(batchSizeHalf),
        )

        scheduleRepository.saveStudyDays(objectId, fetchedDays)

        return fetchedDays.first { day -> day.date == date }.lessons
    }

    private companion object {
        const val batchSize = 40L
        const val batchSizeHalf = batchSize / 2
    }
}