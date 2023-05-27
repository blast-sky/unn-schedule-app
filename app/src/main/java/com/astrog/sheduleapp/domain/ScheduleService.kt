package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.domain.model.ScheduleType
import com.astrog.sheduleapp.domain.model.StudyDay
import java.time.LocalDate

interface ScheduleService {

    suspend fun getSchedule(
        type: ScheduleType,
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<StudyDay>
}