package com.astrog.sheduleapp.domain.scheduleMediator

import com.astrog.sheduleapp.domain.ScheduleRepository
import com.astrog.sheduleapp.domain.ScheduleService
import com.astrog.sheduleapp.domain.model.Cache
import com.astrog.sheduleapp.domain.model.lesson.Lesson
import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.StudyDay
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import javax.inject.Inject

class ServiceRepoMediator @Inject constructor(
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
                val fetchedDays = try {
                    fetchStudyDays(date, objectId, objectType)
                } catch (_: RuntimeException) {
                    return cache.data.lessons
                }
                scheduleRepository.deleteAllCachedDays(objectId)
                saveStudyDaysAndGetCurrent(date, objectId, fetchedDays)
            }

            null -> {
                val fetchedDays = fetchStudyDays(date, objectId, objectType)
                saveStudyDaysAndGetCurrent(date, objectId, fetchedDays)
            }
        }
    }

    private suspend fun fetchStudyDays(
        date: LocalDate,
        objectId: Long,
        objectType: ScheduleType,
    ): List<StudyDay> = coroutineScope {
        async {
            scheduleService.getSchedule(
                objectType,
                objectId,
                date.minusDays(batchSizeHalf),
                date.plusDays(batchSizeHalf),
            )
        }
    }.await()

    private suspend fun saveStudyDaysAndGetCurrent(
        date: LocalDate,
        objectId: Long,
        studyDays: List<StudyDay>,
    ): List<Lesson> {
        scheduleRepository.saveStudyDays(objectId, studyDays)
        return studyDays.first { day -> day.date == date }.lessons
    }

    private companion object {
        const val batchSize = 40L
        const val batchSizeHalf = batchSize / 2
    }
}