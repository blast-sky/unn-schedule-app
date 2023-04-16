package com.astrog.sheduleapp.utils

import com.astrog.sheduleapp.domain.model.SubjectDto
import com.astrog.sheduleapp.util.dateFormatter
import com.astrog.sheduleapp.util.isSubjectActive
import com.astrog.sheduleapp.util.timeFormatter
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


private fun randomString(): String {
    return UUID.randomUUID().toString()
}

class DateTimeUtilsTest {

    private val defaultSubjectDto = SubjectDto(
        auditorium = randomString(),
        dayOfWeek = 100,
        dayOfWeekString = randomString(),
        discipline = randomString(),
        beginLesson = randomString(),
        endLesson = randomString(),
        date = randomString(),
        building = randomString(),
        lecturer = randomString(),
        kindOfWork = randomString(),
        stream = null,
    )

    @Test
    fun `isSubjectActive Must return true When subject is active`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultSubjectDto.copy(
            beginLesson = timeFormatter.format(currentDateTime.minusMinutes(10)),
            endLesson = timeFormatter.format(currentDateTime.plusMinutes(5)),
            date = dateFormatter.format(currentDateTime),
        )

        assertTrue(isSubjectActive(subjectDto))
    }

    @Test
    fun `isSubjectActive Must return false When subject is not active`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultSubjectDto.copy(
            beginLesson = timeFormatter.format(currentDateTime.minusMinutes(10)),
            endLesson = timeFormatter.format(currentDateTime.minusMinutes(5)),
            date = dateFormatter.format(currentDateTime),
        )

        assertFalse(isSubjectActive(subjectDto))
    }

    @Test
    fun `isSubjectActive Must return false When subject is dates are different`() {
        val currentDateTime = LocalDateTime.now()

        val subjectDto = defaultSubjectDto.copy(
            beginLesson = timeFormatter.format(currentDateTime.minusMinutes(10)),
            endLesson = timeFormatter.format(currentDateTime.plusMinutes(5)),
            date = dateFormatter.format(currentDateTime.plusDays(1)),
        )

        assertFalse(isSubjectActive(subjectDto))
    }
}