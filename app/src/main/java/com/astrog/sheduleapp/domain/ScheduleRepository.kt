package com.astrog.sheduleapp.domain

import com.astrog.sheduleapp.internal.schedule.SubjectDto
import java.time.LocalDate

interface ScheduleRepository {

    suspend fun getSchedule(type: String, id: Long, start: LocalDate, finish: LocalDate): List<SubjectDto>

    suspend fun getStudentSchedule(id: Long, start: LocalDate, finish: LocalDate): List<SubjectDto>

    suspend fun getLecturerSchedule(id: Long, start: LocalDate, finish: LocalDate): List<SubjectDto>

    suspend fun getGroupSchedule(id: Long, start: LocalDate, finish: LocalDate): List<SubjectDto>

    suspend fun getAuditoriumSchedule(id: Long, start: LocalDate, finish: LocalDate): List<SubjectDto>
}