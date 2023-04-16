package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.internal.SchedulePreferences
import com.astrog.sheduleapp.internal.schedule.SubjectDto
import java.time.LocalDate
import javax.inject.Inject

class ScheduleLoader @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val schedulePreferences: SchedulePreferences,
) {

    suspend fun loadSchedule(date: LocalDate): List<SubjectDto> {
        if (schedulePreferences.activeId == -1L)
            return emptyList()

        return scheduleRepository.getSchedule(
            schedulePreferences.activeType,
            schedulePreferences.activeId,
            date,
            date
        )
    }
}