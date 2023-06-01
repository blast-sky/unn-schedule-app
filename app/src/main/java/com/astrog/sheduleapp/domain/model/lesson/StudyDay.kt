package com.astrog.sheduleapp.domain.model.lesson

import java.time.LocalDate

data class StudyDay(
    val date: LocalDate,
    val lessons: List<Lesson>,
)
