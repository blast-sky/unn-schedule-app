package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.lesson.ScheduleType
import com.astrog.sheduleapp.domain.model.lesson.StudyDay
import java.time.LocalDate

interface ScheduleService {

    suspend fun getSchedule(
        type: ScheduleType,
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<StudyDay>
}