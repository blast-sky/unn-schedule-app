package com.astrog.sheduleapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.astrog.sheduleapp.domain.model.SubjectDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

@RequiresApi(Build.VERSION_CODES.O)
val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

@RequiresApi(Build.VERSION_CODES.O)
val dateFormatterWithDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd.MM")

@RequiresApi(Build.VERSION_CODES.O)
fun isSubjectActive(subject: SubjectDto): Boolean {
    val currentDateTime = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    val beginDateTime = LocalDateTime.parse("${subject.date} ${subject.beginLesson}", formatter)
    val endDateTime = LocalDateTime.parse("${subject.date} ${subject.endLesson}", formatter)

    return currentDateTime.isAfter(beginDateTime) && currentDateTime.isBefore(endDateTime)
}