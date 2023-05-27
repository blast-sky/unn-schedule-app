package com.astrog.sheduleapp.internal.mapper

import com.astrog.sheduleapp.domain.model.Lesson
import com.astrog.sheduleapp.internal.dto.LessonDto
import com.astrog.sheduleapp.internal.room.entity.LessonEntity

fun Lesson.toLessonDto(): LessonDto = LessonDto(
    auditorium = auditorium,
    dayOfWeek = dayOfWeek,
    dayOfWeekString = dayOfWeekString,
    discipline = discipline,
    beginLesson = beginLesson,
    endLesson = endLesson,
    date = date,
    building = building,
    lecturer = lecturer,
    kindOfWork = kindOfWork,
    stream = stream,
)

fun Lesson.toLessonEntity(cachedDayId: Long): LessonEntity = LessonEntity(
    id = null,
    cachedDayId = cachedDayId,
    auditorium = auditorium,
    dayOfWeek = dayOfWeek,
    dayOfWeekString = dayOfWeekString,
    discipline = discipline,
    beginLesson = beginLesson,
    endLesson = endLesson,
    date = date,
    building = building,
    lecturer = lecturer,
    kindOfWork = kindOfWork,
    stream = stream,
)