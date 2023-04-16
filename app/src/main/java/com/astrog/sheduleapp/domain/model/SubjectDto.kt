package com.astrog.sheduleapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubjectDto(
    val auditorium: String,
    val dayOfWeek: Long,
    val dayOfWeekString: String,
    val discipline: String,
    val beginLesson: String,        // HH:mm
    val endLesson: String,          // HH:mm
    val date: String,               // yyyy.MM.dd
    val building: String,
    val lecturer: String,
    val kindOfWork: String,
    val stream: String?,
) : Parcelable