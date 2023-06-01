package com.astrog.sheduleapp.internal.service

import com.astrog.sheduleapp.domain.ScheduleService
import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.StudyDay
import com.astrog.sheduleapp.util.LocalDateRange
import java.time.LocalDate
import javax.inject.Inject

class DefaultScheduleService @Inject constructor(
    private val client: RuzapiClient,
) : ScheduleService {

    override suspend fun getSchedule(
        type: ScheduleType,
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<StudyDay> {
        val dateToLessons = client.getSchedule(type, id, start, finish)
            .groupBy { lesson -> lesson.date }

        return LocalDateRange(start, finish)
            .map { date -> StudyDay(date, dateToLessons[date] ?: emptyList()) }
    }
}