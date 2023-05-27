package com.astrog.sheduleapp.domain.model

import java.time.LocalDate
import java.time.LocalTime

interface Lesson {
    val auditorium: String
    val dayOfWeek: Long
    val dayOfWeekString: String
    val discipline: String
    val beginLesson: LocalTime
    val endLesson: LocalTime
    val date: LocalDate
    val building: String
    val lecturer: String
    val kindOfWork: String
    val stream: String?

    val isActive: Boolean
        get() {
            if (date != LocalDate.now())
                return false

            val currentTime = LocalTime.now()

            return currentTime.isAfter(beginLesson) && currentTime.isBefore(endLesson)
        }
}