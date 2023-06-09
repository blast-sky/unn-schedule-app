package com.astrog.sheduleapp.internal.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.astrog.sheduleapp.domain.model.lesson.KindOfWork
import com.astrog.sheduleapp.domain.model.lesson.Lesson
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "lesson",
    foreignKeys = [
        ForeignKey(
            entity = StudyDayEntity::class,
            parentColumns = ["id"],
            childColumns = ["cached_day_id"],
            onDelete = CASCADE,
        )
    ],
)
data class LessonEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "cached_day_id", index = true)
    val cachedDayId: Long,
    override val auditorium: String,
    override val dayOfWeek: Long,
    override val dayOfWeekString: String,
    override val discipline: String,
    override val beginLesson: LocalTime,
    override val endLesson: LocalTime,
    @ColumnInfo(index = true)
    override val date: LocalDate,
    override val building: String,
    override val lecturer: String,
    override val kindOfWork: String,
    override val stream: String?,
    @JvmField
    val kindOfWorkType: KindOfWork,
) : Lesson {

    override fun getKindOfWorkType(): KindOfWork {
        return kindOfWorkType
    }
}