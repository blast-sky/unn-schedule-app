package com.astrog.sheduleapp.internal.schedule

import android.annotation.SuppressLint
import com.astrog.sheduleapp.domain.ScheduleRepository
import com.astrog.sheduleapp.internal.RuzapiClient
import com.astrog.sheduleapp.util.AUDITORIUM
import com.astrog.sheduleapp.util.GROUP
import com.astrog.sheduleapp.util.LECTURER
import com.astrog.sheduleapp.util.STUDENT
import com.astrog.sheduleapp.util.dateFormatter
import java.time.LocalDate
import javax.inject.Inject

class DefaultScheduleRepository @Inject constructor(
    private val client: RuzapiClient,
) : ScheduleRepository {

    @SuppressLint("NewApi")
    override suspend fun getSchedule(
        type: String,
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<SubjectDto> = client.getSchedule(
        type,
        id,
        dateFormatter.format(start),
        dateFormatter.format(finish),
    )

    override suspend fun getStudentSchedule(
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<SubjectDto> = getSchedule(STUDENT, id, start, finish)

    override suspend fun getLecturerSchedule(
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<SubjectDto> = getSchedule(LECTURER, id, start, finish)

    override suspend fun getGroupSchedule(
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<SubjectDto> = getSchedule(GROUP, id, start, finish)

    override suspend fun getAuditoriumSchedule(
        id: Long,
        start: LocalDate,
        finish: LocalDate,
    ): List<SubjectDto> = getSchedule(AUDITORIUM, id, start, finish)
}