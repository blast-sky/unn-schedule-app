package com.astrog.sheduleapp.internal.dto

import com.astrog.sheduleapp.domain.model.lesson.KindOfWork
import com.astrog.sheduleapp.domain.model.lesson.Lesson
import com.astrog.sheduleapp.internal.service.typeAdapter.KindOfWorkSerializer
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
    override val kindOfWork: String,
    override val stream: String?,
) : Lesson {

    private lateinit var _kindOfWorkType: KindOfWork

    override fun getKindOfWorkType(): KindOfWork {
        if (!this::_kindOfWorkType.isInitialized) {
            _kindOfWorkType = KindOfWorkSerializer.stringToKindOfWork(kindOfWork)
        }
        return _kindOfWorkType
    }
}