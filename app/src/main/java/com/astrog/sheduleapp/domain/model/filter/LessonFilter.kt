package com.astrog.sheduleapp.domain.model.filter

import com.astrog.sheduleapp.domain.model.lesson.Lesson

interface LessonFilter {
    fun isSatisfy(lesson: Lesson): Boolean
}