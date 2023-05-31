package com.astrog.sheduleapp.internal.dto

import com.astrog.sheduleapp.domain.model.KindOfWork
import com.astrog.sheduleapp.domain.model.Lesson
import java.time.LocalDate
import java.time.LocalTime

data class LessonDto(
    override val auditorium: String,
    override val dayOfWeek: Long,
    override val dayOfWeekString: String,
    override val discipline: String,
    override val beginLesson: LocalTime,        // HH:mm
    override val endLesson: LocalTime,          // HH:mm
    override val date: LocalDate,               // yyyy.MM.dd
    override val building: String,
    override val lecturer: String,
    override val kindOfWork: KindOfWork,
    override val stream: String?,
) : Lesson