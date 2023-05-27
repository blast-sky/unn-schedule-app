package com.astrog.sheduleapp.internal.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "cashed_day")
data class StudyDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "object_id", index = true)
    val objectId: Long,
    val date: LocalDate,
    @ColumnInfo(name = "modified_datetime")
    val modifiedDateTime: LocalDateTime,
)
