package com.astrog.sheduleapp.presentation.schedule.model

import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit


data class Page(
    val date: LocalDate,
    val ordinal: Int,
) {
    constructor(date: LocalDate) : this(date, dateToOrdinal(date))
    constructor(ordinal: Int) : this(ordinalToDate(ordinal), ordinal)

    operator fun plus(count: Int): Page {
        return Page(ordinal + count)
    }

    operator fun minus(count: Int): Page {
        return plus(-count)
    }

    companion object {
        private const val initialOrdinal = Int.MAX_VALUE / 2

        val Initial = Page(initialOrdinal)

        fun ordinalToDate(page: Int): LocalDate {
            val diffInDays = page - initialOrdinal
            return LocalDate.now().plusDays(diffInDays.toLong())
        }

        fun dateToOrdinal(date: LocalDate): Int {
            val now = LocalDate.now()
            return initialOrdinal + ChronoUnit.DAYS.between(now, date).toInt()
        }
    }
}
