package com.astrog.sheduleapp.util

import java.time.LocalDate

operator fun LocalDate.rangeTo(finish: LocalDate): LocalDateRange =
    LocalDateRange(this, finish)

class LocalDateRange(
    private val startDate: LocalDate,
    private val endDate: LocalDate,
) : Iterable<LocalDate>, ClosedRange<LocalDate> {

    override fun iterator(): Iterator<LocalDate> {
        return DateIterator(startDate, endDate)
    }

    private class DateIterator(
        startDate: LocalDate,
        private val endDate: LocalDate,
        ) : Iterator<LocalDate> {

        private var currentDate = startDate

        override fun hasNext(): Boolean {
            return !currentDate.isAfter(endDate)
        }

        override fun next(): LocalDate {
            val date = currentDate
            currentDate = currentDate.plusDays(1)
            return date
        }
    }

    override val endInclusive: LocalDate
        get() = endDate

    override val start: LocalDate
        get() = startDate
}
