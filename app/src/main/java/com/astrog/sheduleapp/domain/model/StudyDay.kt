package com.astrog.sheduleapp.domain.model

import java.time.LocalDate

data class StudyDay(
    val date: LocalDate,
    val lessons: List<Lesson>,
)
