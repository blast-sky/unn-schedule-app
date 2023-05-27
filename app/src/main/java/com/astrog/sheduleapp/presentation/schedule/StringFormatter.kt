package com.astrog.sheduleapp.presentation.schedule

import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateFormatterWithDayOfWeek: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd.MM")

fun dateToStringWithDayOfWeek(date: LocalDate): String {
    return dateFormatterWithDayOfWeek
        .format(date)
        .replaceFirstChar(Char::titlecaseChar)
}