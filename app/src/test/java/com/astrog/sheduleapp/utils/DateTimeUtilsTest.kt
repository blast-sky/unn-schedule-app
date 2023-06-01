package com.astrog.sheduleapp.utils

import com.astrog.sheduleapp.domain.model.lesson.isActive
import com.astrog.sheduleapp.internal.dto.LessonDto
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID


private fun randomString(): String {
    return UUID.randomUUID().toString()
}

class DateTimeUtilsTest {

    private val defaultLessonDto = LessonDto(
        auditorium = randomString(),
        dayOfWeek = 100,
        dayOfWeekString = randomString(),
        discipline = randomString(),
        beginLesson = LocalTime.now(),
        endLesson = LocalTime.now(),
        date = LocalDate.now(),
        building = randomString(),
        lecturer = randomString(),
        kindOfWork = randomString(),
        stream = null,
    )

    @Test
    fun `isSubjectActive Must return true When subject is active`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultLessonDto.copy(
            beginLesson = currentDateTime.toLocalTime().minusMinutes(10),
            endLesson = currentDateTime.toLocalTime().plusMinutes(5),
            date = currentDateTime.toLocalDate(),
        )

        assertTrue(subjectDto.isActive)
    }

    @Test
    fun `isSubjectActive Must return false When subject is not active`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultLessonDto.copy(
            beginLesson = currentDateTime.toLocalTime().minusMinutes(10),
            endLesson = currentDateTime.toLocalTime().minusMinutes(5),
            date = currentDateTime.toLocalDate(),
        )

        assertFalse(subjectDto.isActive)
    }

    @Test
    fun `isSubjectActive Must return false When subject is dates are different`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultLessonDto.copy(
            beginLesson = currentDateTime.toLocalTime().minusMinutes(10),
            endLesson = currentDateTime.toLocalTime().plusMinutes(5),
            date = currentDateTime.plusDays(1).toLocalDate(),
        )

        assertFalse(subjectDto.isActive)
    }
}