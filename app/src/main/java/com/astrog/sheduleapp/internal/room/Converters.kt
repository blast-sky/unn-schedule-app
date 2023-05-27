package com.astrog.sheduleapp.internal.room

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class Converters {

    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun longToLocalDateTime(long: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(long), ZoneOffset.UTC)
    }

    @TypeConverter
    fun localDateToLong(localDate: LocalDate): Long {
        return localDate.toEpochDay()
    }

    @TypeConverter
    fun longToLocalDate(long: Long): LocalDate {
        return LocalDate.ofEpochDay(long)
    }

    @TypeConverter
    fun localTimeToLong(localTime: LocalTime): Long {
        return localTime.toNanoOfDay()
    }

    @TypeConverter
    fun longToLocalTime(long: Long): LocalTime {
        return LocalTime.ofNanoOfDay(long)
    }
}
