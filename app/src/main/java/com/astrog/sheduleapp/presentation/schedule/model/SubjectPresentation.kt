package com.astrog.sheduleapp.presentation.schedule.model

import com.astrog.sheduleapp.domain.model.lesson.Lesson

data class SubjectPresentation(
    val lesson: Lesson,
    val isActive: Boolean,
)